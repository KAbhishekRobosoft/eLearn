package com.eLearn.eLearning.user.refreshToken;
import com.eLearn.eLearning.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    Optional<RefreshToken> findByToken(String token);

    public void deleteAllByUserid(int userId);

    @Modifying
    int deleteByUser(User user);

    Optional<RefreshToken> findByUserid(int userId);

}
