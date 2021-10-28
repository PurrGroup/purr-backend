package group.purr.purrbackend.service.impl;

import group.purr.purrbackend.constant.AuthorMetaConstants;
import group.purr.purrbackend.constant.MagicConstants;
import group.purr.purrbackend.dto.AuthorDTO;
import group.purr.purrbackend.entity.Author;
import group.purr.purrbackend.repository.AuthorRepository;
import group.purr.purrbackend.service.AuthorService;
import group.purr.purrbackend.utils.EncryptUtil;
import group.purr.purrbackend.utils.PurrUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

@Service
@Slf4j
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
        passwordRecord.setOptionValue(EncryptUtil.encryptPassword(password)); //加密后的password
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

        List<Author> profiles = authorRepository.findAll();
        AuthorDTO result = new AuthorDTO();

        for(Author profile: profiles){

            String key = profile.getOptionKey();
            String value = profile.getOptionValue();

            String methodName = key.substring(0,1).toUpperCase() + key.substring(1);
            methodName = methodName.replace("_", "");
            try{
                Method setMethod = AuthorDTO.class.getMethod("set"+methodName, String.class);
                setMethod.invoke(result,value);
            } catch (InvocationTargetException | IllegalAccessException | NoSuchMethodException ignored) {

            }

        }

        //遍历属性，若有null则赋值为""
        Field[] fields = AuthorDTO.class.getFields();
        for(Field field: fields){

            String key = field.getName();
            String value = null;
            String methodName = key.substring(0,1).toUpperCase() + key.substring(1);
            methodName = methodName.replace("_", "");
            try{
                Method m = AuthorDTO.class.getMethod("get"+methodName);
                value = (String) m.invoke(result);
            } catch (InvocationTargetException | NoSuchMethodException | IllegalAccessException e) {

            }

            if(value == null){
                value = "";
                try{
                    Method setMethod = AuthorDTO.class.getMethod("set"+methodName, String.class);
                    setMethod.invoke(result, value);
                } catch (InvocationTargetException | IllegalAccessException | NoSuchMethodException ignored) {

                }
            }
        }

        result.setAvatar(PurrUtils.getAvatarUrl(result.getQq(), result.getEmail(), result.getUsername()));

        return result;
    }

    @Override
    public String getEncryptedPassword(){
        Author encryptedPassword = authorRepository.findAuthorByOptionKey(AuthorMetaConstants.PASSWORD).orElse(new Author());

        return encryptedPassword.getOptionValue();
    }
}
