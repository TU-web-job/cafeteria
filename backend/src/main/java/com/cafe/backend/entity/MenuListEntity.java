/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.cafe.backend.entity;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;
import lombok.Data;

/**
 *
 * @author tu
 */
@Data
@Entity
@Table(name = "menu_list")
public class MenuListEntity {

    @Id @Column(name = "menu_id", nullable=false) @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int menuId;

    @Column(name="menu_name", nullable=false)
    private String menuName;

    @Column(name="menu_price", nullable=false)
    private int menuPrice;

    @Column(name="menu_point", nullable=false)
    private int menuPoint;

    @Column(name="menu_flg", nullable=false) //0 = drink, 1 = food
    private int menuFlg;

    @Lob
    @Basic(fetch = FetchType.LAZY)
    @Column(name = "menu_img", columnDefinition="LONGBLOB")
    private byte[] menuImg;

}
