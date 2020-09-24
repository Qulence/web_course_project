package domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class Exhibit {
    private long id;
    @NonNull
    private String name;
    @NonNull
    private boolean onExposition;
    @NonNull
    private Museum museum;
    @NonNull
    private ExhibitType exhibitType;
}