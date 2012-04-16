package ru.pfo.controller.validator;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import ru.pfo.model.Settings;

public class AdministationFormValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		return Settings.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		Settings settings = (Settings) target;

		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "cronExpresion", "admin-required-cron-expresion",
				"Required field");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "refreshInterval", "admin-required-refresh-interval",
				"Required field");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "numberOfPages", "admin-required-number-of-pages",
				"Required field");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "itemsOnPage", "admin-required-items-on-page",
				"Required field");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "maxLivingLinks", "admin-required-max-living-links",
				"Required field");

		if (!errors.hasFieldErrors("cronExpresion")) {
			String[] split = settings.getCronExpresion().split(" ");
			if (split.length != 6)
				errors.rejectValue("cronExpresion", "admin-cron-exeption", "Invalid format");
		}

	}

}
