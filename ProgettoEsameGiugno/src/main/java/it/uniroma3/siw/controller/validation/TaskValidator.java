package it.uniroma3.siw.controller.validation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import it.uniroma3.siw.model.Task;
import it.uniroma3.siw.services.TaskService;

@Component
public class TaskValidator implements Validator {

	final Integer LUNGHEZZA_MAX_NOME = 20;
	final Integer LUNGHEZZA_MIN_NOME = 3;
	
	final Integer LUNGHEZZA_MAX_DESC = 200;
	final Integer LUNGHEZZA_MIN_DESC = 4;
	
	@Autowired
	TaskService taskService;

	@Override
	public boolean supports(Class<?> clazz) {
		return Task.class.equals(clazz);
	}

	@Override
	public void validate(Object o, Errors errors) {
		Task task = (Task) o;
		String nometask = task.getNometask().trim();
		String descrizione = task.getDescrizione().trim();
		
		if (nometask.trim().isEmpty())
			errors.rejectValue("nometask", "necessario");
		else if (nometask.length() < LUNGHEZZA_MIN_NOME || nometask.length() > LUNGHEZZA_MAX_NOME)
			errors.rejectValue("nometask", "dimensione");
		
		if (descrizione.trim().isEmpty())
			errors.rejectValue("descrizione", "necessario");
		else if (descrizione.length()<LUNGHEZZA_MIN_DESC || descrizione.length() > LUNGHEZZA_MAX_DESC)
			errors.rejectValue("descrizione", "dimensione");
		
	}
}
