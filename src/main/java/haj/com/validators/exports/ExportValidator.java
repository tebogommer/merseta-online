package haj.com.validators.exports;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.apache.commons.beanutils.BeanUtils;

import haj.com.constants.HAJConstants;
import haj.com.utils.ReflectionUtils;

// TODO: Auto-generated Javadoc
/**
 * The Class IDValidator.
 */
public class ExportValidator implements ConstraintValidator<ExportValidation, Object> {

	private String       failedMessage;
	private List<String> messages       = null;
	private boolean      validateEntity = HAJConstants.ENTITY_VALIDATION_ON;

	@Override
	public void initialize(ExportValidation annotation) {
		failedMessage = annotation.message();
	}

	@Override
	public boolean isValid(Object value, ConstraintValidatorContext context) {
		if (validateEntity) {
			messages = new LinkedList<>();
			try {
				validateFields(value);
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			} catch (NoSuchMethodException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
			if (!messages.isEmpty()) {
				context.disableDefaultConstraintViolation();
				// context.buildConstraintViolationWithTemplate("<b>" + failedMessage +
				// "</b>").addConstraintViolation();
				String error = "<b>" + failedMessage + "</b><br/><ul>";
				for (String message : messages) {
					error += "<li>" + message + "</li>";
				}
				error += "</ul>";
				context.buildConstraintViolationWithTemplate(error).addConstraintViolation();
			}
			return messages.isEmpty();
		} else {
			return true;
		}

	}

	private void validateFields(Object value) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException, InstantiationException {
		if (value != null) {
			List<Field> fields = ReflectionUtils.getFieldsWithAnnotations(value.getClass().getDeclaredFields(), SETMISFieldValidation.class);
			for (Field field : fields) {
				SETMISFieldValidation pr         = field.getAnnotation(SETMISFieldValidation.class);
				String                fieldValue = BeanUtils.getProperty(value, pr.fieldName());
				if (fieldValue == null) fieldValue = "null";

				if ((pr.fieldValue().equals("NOT_NULL") && !fieldValue.equalsIgnoreCase("NULL")) || fieldValue.equalsIgnoreCase(pr.fieldValue())) {
					if (pr.process()) {
						validateFields(ReflectionUtils.getFieldValue(value, field));
					} else {
						Object  obj        = pr.className().newInstance();
						Method  m          = pr.className().getDeclaredMethod(pr.method(), pr.paramClass());
						boolean validField = (boolean) m.invoke(obj, ReflectionUtils.getFieldValue(value, field));
						if (!validField) {
							messages.add(pr.message());
						}
					}
				}
			}
		}

	}

}