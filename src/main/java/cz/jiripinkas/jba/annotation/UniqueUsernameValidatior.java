package cz.jiripinkas.jba.annotation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import cz.jiripinkas.jba.repository.UserRepository;

public class UniqueUsernameValidatior implements ConstraintValidator<UniqueUsername, String> {

	@Autowired
	private UserRepository uerRepository;
	
	@Override
	public void initialize(UniqueUsername constraintAnnotation) {
		
	}

	@Override
	public boolean isValid(String userName, ConstraintValidatorContext context) {
		if(uerRepository == null)
			return true;
		return uerRepository.findByName(userName) == null;
	}

}
