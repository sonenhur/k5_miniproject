package miniproject.stellanex.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
@Entity(name = "Dailyboxoffice")
public class Dailyboxoffice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Getter and Setter methods
    @Column(nullable = false)
    private int rank;
    @Column(nullable = false)
    private int rankInten;
    @Column(nullable = false)
    private String rankOldAndNew;
    @Column(nullable = false)
    private String movieCd;
    @Column(nullable = false)
    private String movieNm;
    @Column(nullable = false)
    private Date openDt;
    @Column(nullable = false)
    private long salesAmt;
    @Column(nullable = false)
    private double salesShare;
    @Column(nullable = false)
    private long salesInten;
    @Column(nullable = false)
    private double salesChange;
    @Column(nullable = false)
    private long salesAcc;
    @Column(nullable = false)
    private int audiCnt;
    @Column(nullable = false)
    private int audiInten;
    @Column(nullable = false)
    private double audiChange;
    @Column(nullable = false)
    private int audiAcc;
    @Column(nullable = false)
    private int scrnCnt;
    @Column(nullable = false)
    private int showCnt;

}