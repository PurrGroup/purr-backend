package group.purr.purrbackend.service.impl;

import group.purr.purrbackend.constant.AuthorMetaConstants;
import group.purr.purrbackend.constant.MagicConstants;
import group.purr.purrbackend.entity.Author;
import group.purr.purrbackend.repository.AuthorRepository;
import group.purr.purrbackend.service.AuthorService;
import group.purr.purrbackend.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

@Service
public class AuthorServiceImpl implements AuthorService {

    final
    AuthorRepository authorRepository;

    public AuthorServiceImpl(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    @Override
    public Boolean createBy(String username, String password, String email) {
        Assert.notNull(username, "Username cannot be null.");
        Assert.notNull(password, "Password cannot be null.");
        Assert.notNull(email, "Email cannot be null.");

        Author usernameRecord = new Author();
        usernameRecord.setOptionKey(AuthorMetaConstants.USER_NAME);
        usernameRecord.setOptionValue(username);
        authorRepository.save(usernameRecord);

        Author passwordRecord = new Author();
        passwordRecord.setOptionKey(AuthorMetaConstants.PASSWORD);
        passwordRecord.setOptionValue(JwtUtil.tokenGeneration(password)); //加密后的password
        authorRepository.save(passwordRecord);

        Author emailRecord = new Author();
        emailRecord.setOptionKey(AuthorMetaConstants.EMAIL);
        emailRecord.setOptionValue(email);
        authorRepository.save(emailRecord);

        Author descriptionRecord = new Author();
        descriptionRecord.setOptionKey(AuthorMetaConstants.DESCRIPTION);
        descriptionRecord.setOptionValue(MagicConstants.DEFAULT_DESCRIPTION);
        authorRepository.save(descriptionRecord);

        return true;
    }

    @Override
    public void deleteAll() {
        authorRepository.deleteAll();
    }
}
