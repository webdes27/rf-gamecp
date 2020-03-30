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
public class GiveItem {
    private List<Item> items;
    private float chance;
}
