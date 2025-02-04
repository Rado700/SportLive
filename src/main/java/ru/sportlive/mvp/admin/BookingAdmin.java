//package ru.sportlive.mvp.admin;
//
//import com.vaadin.flow.component.button.Button;
//import com.vaadin.flow.component.grid.Grid;
//import com.vaadin.flow.component.notification.Notification;
//import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
//import com.vaadin.flow.component.orderedlayout.VerticalLayout;
//import com.vaadin.flow.component.textfield.TextField;
//import com.vaadin.flow.router.Route;
//import org.springframework.beans.factory.annotation.Autowired;
//import ru.sportlive.mvp.models.Booking;
//import ru.sportlive.mvp.repository.BookingRepository;
//
//@Route("db/admin/bookings")
//public class BookingAdmin extends VerticalLayout {
//
//    private final BookingRepository bookingRepository;
//
//    private Grid<Booking> grid = new Grid<>(Booking.class);
//    private TextField scheduleField = new TextField("Schedule");
//    private TextField userField = new TextField("User");
//    private Button saveButton = new Button("Save");
//    private Button deleteButton = new Button("Delete");
//
//    @Autowired
//    public BookingAdmin(BookingRepository bookingRepository) {
//        this.bookingRepository = bookingRepository;
//
//        // Инициализация таблицы
//        grid.setColumns("id", "schedule", "user"); // Укажите нужные поля для отображения
//        grid.setItems(bookingRepository.findAll());
//        grid.asSingleSelect().addValueChangeListener(event -> {
//            Booking selectedBooking = event.getValue();
//            if (selectedBooking != null) {
//                scheduleField.setValue(selectedBooking.getSchedule() != null ? selectedBooking.getSchedule().toString() : "");
//                userField.setValue(selectedBooking.getUser() != null ? selectedBooking.getUser().toString() : "");
//            }
//        });
//
//        // Добавление функционала для кнопок
//        saveButton.addClickListener(event -> saveBooking());
//        deleteButton.addClickListener(event -> deleteBooking());
//
//        // Компоновка
//        HorizontalLayout formLayout = new HorizontalLayout(scheduleField, userField, saveButton, deleteButton);
//        add(grid, formLayout);
//    }
//
//    private void saveBooking() {
//        try {
//            Booking booking = new Booking();
//            // Здесь можно добавить преобразование scheduleField и userField
//            bookingRepository.save(booking);
//            grid.setItems(bookingRepository.findAll());
//            Notification.show("Booking saved!");
//        } catch (Exception e) {
//            Notification.show("Error saving booking: " + e.getMessage());
//        }
//    }
//
//    private void deleteBooking() {
//        Booking selected = grid.asSingleSelect().getValue();
//        if (selected != null) {
//            bookingRepository.delete(selected);
//            grid.setItems(bookingRepository.findAll());
//            Notification.show("Booking deleted!");
//        } else {
//            Notification.show("No booking selected!");
//        }
//    }
//}
//
