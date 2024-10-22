package com.travelbnb.travelbnb.service;



import com.travelbnb.travelbnb.dto.BookingDto;
import com.travelbnb.travelbnb.entity.AppUser;
import com.travelbnb.travelbnb.entity.Booking;
import com.travelbnb.travelbnb.entity.Property;
import com.travelbnb.travelbnb.exception.ResourceNotException;
import com.travelbnb.travelbnb.repository.AppUserRepository;
import com.travelbnb.travelbnb.repository.BookingRepository;
import com.travelbnb.travelbnb.repository.PropertyEntityRepository;
import com.twilio.rest.proxy.v1.service.PhoneNumber;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.Optional;

@Service
public class BookingServiceIMPL implements BookingService{

    private static final String PDF_DIRECTORY= "E://travelbnb_pdf//";


    private BookingRepository bookingRepository ;
    private PropertyEntityRepository propertyEntityRepository ;
    private final AppUserRepository appUserRepository;
    private PDFService pdfService ;
    private BucketService bucketService ;
    private TwilioService twilioService ;




    public BookingServiceIMPL(BookingRepository bookingRepository,
                              PropertyEntityRepository propertyEntityRepository,

                              AppUserRepository appUserRepository, PDFService pdfService, BucketService bucketService, TwilioService twilioService) {
        this.bookingRepository = bookingRepository;
        this.propertyEntityRepository = propertyEntityRepository;
        this.appUserRepository = appUserRepository;
        this.pdfService = pdfService;
        this.bucketService = bucketService;
        this.twilioService = twilioService;
    }

    @Override
    public BookingDto CreateBooking(long propertyId, AppUser user, BookingDto bookingDto) {
        Optional<Property> byId = propertyEntityRepository.findById(propertyId);
        if (byId.isPresent()) {
            Property property = byId.get();

            int nightlyPrice = property.getPrice();
            int totalPrice = nightlyPrice * bookingDto.getTotalNights();
            int gstAmount = (totalPrice * 18) / 100;
            int finalPrice = totalPrice + gstAmount;

            bookingDto.setPrice(finalPrice);
            bookingDto.setProperty(property);


            Optional<AppUser> byUsername = appUserRepository.findByUsername(user.getUsername());
            if(byUsername.isPresent()) {

                AppUser appUser = byUsername.get();

                bookingDto.setAppUser(appUser);

                Booking booking = dtoToEntity(bookingDto);
                Booking saveBooking = bookingRepository.save(booking);
                BookingDto dto = entityToDto(saveBooking);
                boolean  generated = pdfService.generatePDF("E://travelbnb_pdf//" + "booking-confirmation-id" + dto.getId() + ".pdf", dto);
                if (generated){
                     try{
                         MultipartFile file = BookingServiceIMPL.convert("E://travelbnb_pdf//" + "booking-confirmation-id" + dto.getId() + ".pdf");
                         //
                         String travelbnbUrl = bucketService.uploadFile(file, "tavelbnb01");


                         //for sms
                         String smsId = twilioService.sendSms(bookingDto.getMobile(),
                                 "Your booking is confirmed. Click for the details:   " + travelbnbUrl);
                         System.out.println(smsId);


                         //for whatsapp
                         String whatsappId = twilioService.sendWhatsAppMessage(bookingDto.getMobile(),
                                 "Your booking is confirmed..Click for deatils: " + travelbnbUrl);
                         System.out.println(whatsappId);

                         System.out.println("SMS SID: " + smsId);
                         System.out.println("WhatsApp SID: " + whatsappId);


                     }catch (Exception e){
                         e.printStackTrace();
                     }
                }

                return dto;
            }else {
                throw new ResourceNotException("User name Not found"+user);
            }
        }
        throw new ResourceNotException("PropertyId Not Found :"+propertyId);
    }



    public Booking dtoToEntity(BookingDto bookingDto ){
        Booking booking = new Booking();
        booking.setName(bookingDto.getName());
        booking.setEmail(bookingDto.getEmail());
        booking.setMobile(bookingDto.getMobile());
        booking.setPrice(bookingDto.getPrice());
        booking.setTotalNights(bookingDto.getTotalNights());
        booking.setProperty(bookingDto.getProperty());
        booking.setAppUser(bookingDto.getAppUser());
        return booking;
    }

    public BookingDto entityToDto(Booking booking ){
        BookingDto dto = new BookingDto();
        dto.setId(booking.getId());
        dto.setName(booking.getName());
        dto.setEmail(booking.getEmail());
        dto.setMobile(booking.getMobile());
        dto.setPrice(booking.getPrice());
        dto.setTotalNights(booking.getTotalNights());
        dto.setProperty(booking.getProperty());
        dto.setAppUser(booking.getAppUser());
        return dto;
    }



    //convert file to multipart file by taking file path
    public static MultipartFile convert(String filePath) throws IOException {

        //Load the file from the specified path
        File file = new File(filePath);

        //Read The file content into a byte array
        byte[]fileContent= Files.readAllBytes(file.toPath());

        //Convert byte array to Resource (ByteArrayResource)
        Resource resource=new ByteArrayResource(fileContent);

        //Create MultipartFile From Resource
        MultipartFile multipartFile=new MultipartFile() {
            @Override
            public String getName() {
                return file.getName();
            }

            @Override
            public String getOriginalFilename() {
                return file.getName();
            }

            @Override
            public String getContentType() {
                return null;
            }

            @Override
            public boolean isEmpty() {
                return fileContent.length==0;
            }

            @Override
            public long getSize() {
                return fileContent.length;
            }

            @Override
            public byte[] getBytes() throws IOException {
                return fileContent;
            }

            @Override
            public InputStream getInputStream() throws IOException {
                return null;
            }

            @Override
            public void transferTo(File dest) throws IOException, IllegalStateException {
                Files.write(dest.toPath(),fileContent);
            }
        };
        return multipartFile;
    }



}

