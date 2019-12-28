package com.lsy.dbcompare.compare.content;

import java.awt.FlowLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
 
public class Test extends JFrame {
    public Test() {
        this.setTitle("测试");
        this.setSize(400, 400);
        getContentPane().setLayout(null);
        J.setLocation(100, 100);
        J.setSize(30, 40);
        this.add(J);
        this.setVisible(true);
 
    }
    public static void main(String args[]){
        Test t=new Test();
    }
 
    private JButton J = new JButton("测试");
 
}
