package group.purr.purrbackend.service.impl;

import group.purr.purrbackend.constant.AuthorMetaConstants;
import group.purr.purrbackend.entity.Author;
import group.purr.purrbackend.repository.AuthorRepository;
import group.purr.purrbackend.service.AuthorService;
import group.purr.purrbackend.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

@Service
public class AuthorServiceImpl implements AuthorService {

    @Autowired
    AuthorRepository authorRepository;

    @Override
    public boolean createBy(String username, String password, String email) {
        Assert.notNull(username, "Username cannot be null.");
        Assert.notNull(password, "Password cannot be null.");
        Assert.notNull(email, "Email cannot be null.");

        Author usernameRecord = new Author();
        usernameRecord.setOptionKey(AuthorMetaConstants.userName);
        usernameRecord.setOptionValue(username);
        authorRepository.save(usernameRecord);

        Author passwordRecord = new Author();
        passwordRecord.setOptionKey(AuthorMetaConstants.password);
        passwordRecord.setOptionValue(JwtUtil.tokenGeneration(password)); //加密后的password
        authorRepository.save(passwordRecord);

        Author emailRecord = new Author();
        emailRecord.setOptionKey(AuthorMetaConstants.email);
        emailRecord.setOptionValue(email);
        authorRepository.save(emailRecord);

        Author descriptionRecord = new Author();
        descriptionRecord.setOptionKey(AuthorMetaConstants.description);
        descriptionRecord.setOptionValue("这是默认的描述");
        authorRepository.save(descriptionRecord);

        return true;
    }
}
