package br.com.projects.domain.business.publico.attachment;

import br.com.projects.domain.business.Descritivel;
import br.com.projects.domain.business.publico.binary.DBinary;
import br.com.projects.domain.business.validation.NamedValidator;
import br.com.projects.domain.business.validation.ValidationBuilder;
import br.com.projects.domain.business.validation.impl.NaoBrancoValidator;
import br.com.projects.domain.business.validation.impl.TamanhoMaximoValidator;
import br.com.projects.domain.business.validation.impl.TamanhoMinimoValidator;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class DAttachment implements Descritivel {

    @EqualsAndHashCode.Include
    private Integer id;
    private DBinary binary;
    private String name;
    private String mimeType;
    private String url;
    private String checksum;

    public DAttachment(Integer id){
        this.id = id;
    }

    public void validate(){
        new ValidationBuilder()
                .add(new NamedValidator<>("Name", new NaoBrancoValidator()), this.name)
                .add(new NamedValidator<>("Name", new TamanhoMinimoValidator(3)), this.name)
                .add(new NamedValidator<>("Name", new TamanhoMaximoValidator(50)), this.name)
                .add(new NamedValidator<>("MimeType", new NaoBrancoValidator()), this.mimeType)
                .add(new NamedValidator<>("MimeType", new TamanhoMinimoValidator(3)), this.mimeType)
                .add(new NamedValidator<>("MimeType", new TamanhoMaximoValidator(50)), this.mimeType)
                .add(new NamedValidator<>("Url", new NaoBrancoValidator()), this.url)
                .add(new NamedValidator<>("Checksum", new NaoBrancoValidator()), this.checksum)
                .validate();
    }

    @Override
    public String getDescricao() {
        return null;
    }
}
