package br.com.rfreforged.ReforgedGCP.model.servidor;

import br.com.rfreforged.ReforgedGCP.model.View;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PatchConfig {
    @JsonView(View.Exclude.class)
    private boolean needHash;
    private List<PatchFile> list;
}
