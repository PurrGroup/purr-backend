package group.purr.purrbackend.repository;

import group.purr.purrbackend.entity.Media;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface MediaRepository extends JpaRepository<Media, String> {

    Long countByDeleteTimeNull();

    Long countByDeleteTimeNotNull();

    Long countByFileCategory(String category);

    Optional<Media> findByUrl(String linkName);

    @Query(value = "select count(file_category) from media", nativeQuery = true)
    Long countAll();

    @Query(value = "select count(file_category) from media where file_category in (?) ;", nativeQuery = true)
    Long countByOnlyCategory(String whereCondition);

    @Query(value = "select count(file_category) from media where file_category in (?) and delete_time is not null ;", nativeQuery = true)
    Long countByCategoryAndDeleteTimeNotNull(String whereCondition);

    @Query(value = "select count(file_category) from media where file_category in (?) and delete_time is null ;", nativeQuery = true)
    Long countByCategoryAndDeleteTimeNull(String whereCondition);

    Media findByID(Long ID);

    @Query(value = "select DISTINCT file_category from media ", nativeQuery = true)
    List<String> findAllCategory();

    @Query(value = "select * from media where delete_time is null limit ?,?", nativeQuery = true)
    List<Media> findAllMediaExceptDeletedByPage(Integer offset, Integer num);

    @Query(value = "select * from media where file_category = ? and delete_time is null limit ?,?", nativeQuery = true)
    List<Media> findAllMediaWithCategoryExceptDeletedByPage(String category, Integer offset, Integer num);

    @Query(value = "select * from media where delete_time is not null limit ?,? ", nativeQuery = true)
    List<Media> findAllMediaDeleted(Integer offset, Integer pageSize);


    @Query(value = "select * from media limit ?, ?", nativeQuery = true)
    List<Media> findAll(Integer offset, Integer num);

    @Query(value = "select * from media where file_category in (?) limit ?,? ;", nativeQuery = true)
    List<Media> findAllMediaByOnlyCategory(String whereCondition, Integer offset, Integer num);

    @Query(value = "select * from media where delete_time is null limit ?,? ;", nativeQuery = true)
    List<Media> findAllMediaByDeleteTimeNull(Integer offset, Integer num);

    @Query(value = "select * from media where delete_time is not null limit ?,? ;", nativeQuery = true)
    List<Media> findAllMediaByDeleteTimeNotNull(Integer offset, Integer num);

    @Query(value = "select * from media where file_category in (?) and delete_time is null limit ?,? ;", nativeQuery = true)
    List<Media> findAllMediaByCategoryAndDeleteTimeNull(String whereCondition, Integer offset, Integer num);

    @Query(value = "select * from media where file_category in (?) and delete_time is not null limit ?,? ;", nativeQuery = true)
    List<Media> findAllMediaByCategoryAndDeleteTimeNotNull(String whereCondition, Integer offset, Integer num);

}
