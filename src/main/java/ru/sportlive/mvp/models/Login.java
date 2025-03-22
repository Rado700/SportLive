package ru.sportlive.mvp.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.sportlive.mvp.dto.input.LoginDTO;
import ru.sportlive.mvp.dto.output.CouchInfoDTO;
import ru.sportlive.mvp.dto.output.LoginInfoDTO;
import ru.sportlive.mvp.dto.output.UserInfoDTO;

import java.util.HashSet;
import java.util.Set;

@Entity
public class Login {

    @Getter
    @Setter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Getter
    @Setter
    private String login ;
    @Getter
    @Setter
    private String password;

    @Getter
    @Setter
    private String telegramId;

    public Login(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public Login() {
    }

    @Getter
    @Setter
    @JsonBackReference
    @OneToOne(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", foreignKey = @ForeignKey(
            foreignKeyDefinition = "FOREIGN KEY (user_id) REFERENCES \"user\" ON DELETE CASCADE ON UPDATE CASCADE"
    ))
    private User user;

    @Getter
    @Setter
    @JsonBackReference
    @OneToOne(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    @JoinColumn(name = "couch_id", foreignKey = @ForeignKey(
            foreignKeyDefinition = "FOREIGN KEY (couch_id) REFERENCES Couch ON DELETE CASCADE ON UPDATE CASCADE"
   ))
    private Couch couch;

    @Getter
    @Setter
    @JsonManagedReference
    @OneToMany(mappedBy = "login", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<Transaction> transactionList = new HashSet<>();


    public LoginInfoDTO getLoginInfo (){
        UserInfoDTO userInfoDTO = null;
        CouchInfoDTO couchInfoDTO = null;
        if (user != null){
            userInfoDTO = user.getUserInfo();
        }
        if (couch != null){
            couchInfoDTO = couch.getCouchInfo();
        }
        return new LoginInfoDTO(id,userInfoDTO,couchInfoDTO);
    }
}
