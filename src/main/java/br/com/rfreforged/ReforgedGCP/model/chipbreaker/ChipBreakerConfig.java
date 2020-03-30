package br.com.rfreforged.ReforgedGCP.model.chipbreaker;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ChipBreakerConfig {
    private String serverStateUrl;
    private List<GiveItem> giveItems;
}