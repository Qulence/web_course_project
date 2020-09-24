package domain;

import lombok.*;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class Author {
    private long id;
    @NonNull
    private String fullName;
}