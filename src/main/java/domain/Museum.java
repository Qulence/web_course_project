package domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class Museum {
    private long id;
    @NonNull
    private String name;
    @NonNull
    private String address;
    @NonNull
    private String fullNameOfTheHead;
    @NonNull
    private String phoneNumber;
    @NonNull
    private MuseumType museumType;
}