package it.uniroma3.siw.controller.validation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import it.uniroma3.siw.model.Tag;
import it.uniroma3.siw.services.TagService;


@Component
public class TagValidator implements Validator {
	
	final Integer LUNGHEZZA_MAX_NOME = 20;
	final Integer LUNGHEZZA_MIN_NOME = 3;
	final Integer LUNGHEZZA_MAX_DESC = 200;
	final Integer LUNGHEZZA_MIN_DESC = 4;

	@Autowired
	TagService tagService;
	
	@Override
	public boolean supports(Class<?> clazz) {
		return Tag.class.equals(clazz);
	}

	@Override
	public void validate(Object o, Errors errors) {
		
		Tag tag = (Tag) o;
		String nome = tag.getNome().trim();
		String descrizione = tag.getDescrizione().trim();
		String colore = tag.getColore().trim();
		
		if (nome.trim().isEmpty())
			errors.rejectValue("nome", "necessario");
		else if (nome.length() < LUNGHEZZA_MIN_NOME || nome.length() > LUNGHEZZA_MAX_NOME)
			errors.rejectValue("nome", "dimensione");
		
		if (descrizione.trim().isEmpty())
			errors.rejectValue("descrizione", "necessario");
		else if (descrizione.length()<LUNGHEZZA_MIN_DESC || descrizione.length() > LUNGHEZZA_MAX_DESC)
			errors.rejectValue("descrizione", "dimensione");
		
		if (colore.trim().isEmpty())
			errors.rejectValue("descrizione", "necessario");
		else if (colore.length()<LUNGHEZZA_MIN_NOME || colore.length() > LUNGHEZZA_MAX_NOME)
			errors.rejectValue("colore", "dimensione");
	}

}
