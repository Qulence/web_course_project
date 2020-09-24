package domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;

@Data
@AllArgsConstructor
public class AuthorExhibit {
    @NonNull
    private Author author;
    @NonNull
    private Exhibit exhibit;
}
