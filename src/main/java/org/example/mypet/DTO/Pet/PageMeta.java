package org.example.mypet.DTO.Pet;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageMeta {
    private int page;
    private int size;
    private long totalElements;
    private int totalPages;
    private List<String> sort;
    private boolean last;
}
