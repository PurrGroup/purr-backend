package group.purr.purrbackend.service.impl;

import group.purr.purrbackend.constant.AuthorMetaConstants;
import group.purr.purrbackend.constant.MagicConstants;
import group.purr.purrbackend.dto.AuthorDTO;
import group.purr.purrbackend.entity.Author;
import group.purr.purrbackend.repository.AuthorRepository;
import group.purr.purrbackend.service.AuthorService;
import group.purr.purrbackend.utils.JwtUtil;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

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

        Author qqRecord = new Author();
        qqRecord.setOptionKey(AuthorMetaConstants.QQ);
        qqRecord.setOptionValue("");

        return true;
    }

    @Override
    public void deleteAll() {
        authorRepository.deleteAll();
    }

    @Override
    public AuthorDTO getProfile() {

        Author userName = authorRepository.findAuthorByOptionKey(AuthorMetaConstants.USER_NAME);
        Author email = authorRepository.findAuthorByOptionKey(AuthorMetaConstants.EMAIL);
        Author description = authorRepository.findAuthorByOptionKey(AuthorMetaConstants.DESCRIPTION);
        Author qq = authorRepository.findAuthorByOptionKey(AuthorMetaConstants.QQ);

//        String avatar = "https://www.gravatar.com/avatar/" + md5Hex(email.getOptionValue());
        String avatar = "https://sdn.geekzu.org/avatar/" + md5Hex(email.getOptionValue());

        AuthorDTO result = new AuthorDTO();
        result.setUsername(userName.getOptionValue());
        result.setEmail(email.getOptionValue());
        result.setDescription(description.getOptionValue());
        result.setQq("");
        result.setAvatar(avatar);

        return result;
    }

    public static String hex(byte[] array) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < array.length; ++i) {
            sb.append(Integer.toHexString((array[i]
                    & 0xFF) | 0x100).substring(1,3));
        }
        return sb.toString();
    }
    public static String md5Hex (String message) {
        try {
            MessageDigest md =
                    MessageDigest.getInstance("MD5");
            return hex (md.digest(message.getBytes("CP1252")));
        } catch (NoSuchAlgorithmException | UnsupportedEncodingException ignored) {
        }
        return null;
    }

}
