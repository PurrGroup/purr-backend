package group.purr.purrbackend.service;

import group.purr.purrbackend.dto.AuthorDTO;

public interface AuthorService {
    /**
     * 下载时创建用户信息
     *
     * @param username
     * @param password
     * @param email
     * @return true/false 是否创建成功
     */
    Boolean createBy(String username, String password, String email);

    void deleteAll();

    AuthorDTO getProfile();

    String getEncryptedPassword();
}
