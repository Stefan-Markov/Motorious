package projectdefence.validators.password;

import org.passay.*;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Arrays;

import com.google.common.base.Joiner;

public class PasswordConstraintValidator implements ConstraintValidator<ValidPassword, String> {

    @Override
    public void initialize(ValidPassword arg0) {
    }

    @Override
    public boolean isValid(final String password, final ConstraintValidatorContext context) {

        final PasswordValidator validator = new PasswordValidator(Arrays.asList(
                new LengthRule(6, 30),
                new LowercaseCharacterRule(1)));
//                new DigitCharacterRule(1),
//                new UppercaseCharacterRule(1),
//                new SpecialCharacterRule(1),
//                new NumericalSequenceRule(3, false),
//                new AlphabeticalSequenceRule(3, false),
//                new QwertySequenceRule(3, false),
//                new WhitespaceRule()));
        final RuleResult result = validator.validate(new PasswordData(password));
        if (result.isValid()) {
            return true;
        }
        context.disableDefaultConstraintViolation();
        context.buildConstraintViolationWithTemplate(Joiner.on(",").join(validator.getMessages(result))).addConstraintViolation();
        return false;
    }
}