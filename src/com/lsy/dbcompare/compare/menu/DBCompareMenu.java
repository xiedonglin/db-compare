package com.lsy.dbcompare.compare.menu;

import java.awt.Cursor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import com.lsy.dbcompare.compare.content.DBCompareContent;
import com.lsy.dbcompare.entity.DbType;

/**
 * <h4>功能描述：数据比对-菜单栏
 * <h5>
 * 
 * <pre>
 * &#64;author 纳川
 * 2018年5月14日
 * email 1029243332@qq.com
 * </pre>
 * 
 * </h5>
 */
public class DBCompareMenu extends JMenuBar {
    private static final long serialVersionUID = -6052987124251445379L;

    public DBCompareMenu() {
        this.init();
        this.addMenus();
    }

    private void init() {
        this.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }

    // 构建菜单
    private void addMenus() {
        JMenu menuConn = this.getConnMenu();
        JMenu menuCompare = this.getCompareMenu();
        JMenu menuDownload = this.getDownloadMenu();
        JMenu menuTask = this.getTaskMenu();

        this.add(menuConn);
        this.add(menuCompare);
        this.add(menuDownload);
        this.add(menuTask);
    }

    // 菜单：连接
    private JMenu getConnMenu() {
        JMenu menuConn = new JMenu("连接(C)");
        menuConn.setMnemonic('C');
        // 创建菜单项
        JMenuItem mysqlItem = new JMenuItem(DbType.DB_MYSQL);
        JMenuItem oracleItem = new JMenuItem(DbType.DB_ORACLE);
        JMenuItem postgresItem = new JMenuItem(DbType.DB_POSTGRES);
        menuConn.add(mysqlItem);
        menuConn.add(oracleItem);
        menuConn.add(postgresItem);

        mysqlItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new ConnMySqlGUI();
            }
        });

        oracleItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new ConnOracleGUI();
            }
        });

        postgresItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new ConnPostgresGUI();
            }
        });

        return menuConn;
    }

    // 菜单：比较
    private JMenu getCompareMenu() {
        JMenu menuCompare = new JMenu("比较(P)");
        menuCompare.setMnemonic('P');

        menuCompare.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                new CompareGUI();
            }
        });

        return menuCompare;
    }

    // 菜单：任务
    private JMenu getTaskMenu() {
        JMenu menuTask = new JMenu("任务(R)");
        menuTask.setMnemonic('R');
        JMenuItem item1 = new JMenuItem("任务管理(G)");
        JMenuItem item2 = new JMenuItem("结果查看(L)");
        menuTask.add(item1);
        menuTask.add(item2);
        // 任务管理菜单
        item1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new TaskManagerGUI();
            }
        });
        // 结果查看
        item2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.out.println("结果查看");
            }
        });
        /* item2.addMouseListener(new MouseAdapter() {
        	@Override
        	public void mouseClicked(MouseEvent e) {
        		//DBCompareContent.downloadCompareData();
        		System.out.println("结果查看");
        	}
        });*/
        return menuTask;
    }

    // 菜单：下载
    private JMenu getDownloadMenu() {
        JMenu menuDownload = new JMenu("下载(D)");
        menuDownload.setMnemonic('D');
        menuDownload.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                DBCompareContent.downloadCompareData();
            }
        });
        return menuDownload;
    }
}
