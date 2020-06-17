package it.uniroma3.siw.services;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.uniroma3.siw.model.Commento;
import it.uniroma3.siw.model.Tag;
import it.uniroma3.siw.model.Task;
import it.uniroma3.siw.repository.TaskRepository;

@Service
public class TaskService {

	@Autowired
	private TaskRepository taskRepository;

	@Transactional
	public Task getTask(Long id) {
		Optional<Task> result = this.taskRepository.findById(id);
		return result.orElse(null);
	}

	@Transactional
	public void deleteTask(Task task) {
		this.taskRepository.delete(task);
	}

	@Transactional
	public Task saveTask(Task task) {
		return this.taskRepository.save(task);
	}

	@Transactional
	public Task aggiungiTagATask (Task task, Tag tag) {
		task.aggiungiTag(tag);
		return this.taskRepository.save(task);
	}

	@Transactional
	public Task aggiungiCommentoAlTask (Task task, Commento commento) {
		task.aggiungiCommento(commento);
		return this.taskRepository.save(task);
	}

}
