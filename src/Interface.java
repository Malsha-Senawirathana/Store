import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class Interface {
    private JPanel Main;
    private JTextField txtname;
    private JTextField txtprice;
    private JButton saveButton;
    private JButton delectButton;
    private JButton updateButton;
    private JTextField txtpid;
    private JTextField txtqty;
    private JButton searchbutton;

    public static void main(String[] args) {
        JFrame frame = new JFrame("Interface");
        frame.setContentPane(new Interface().Main);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }


    Connection con;
    PreparedStatement pst;
    public Interface() {
            Connect();

    // enter the data that save the details
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               String name,price,qty;

               name = txtname.getText();
               price = txtprice.getText();
               qty = txtqty.getText();

               try{
                   pst = con.prepareStatement("insert into products(pname,price,qty)values(?,?,?)");
                   pst.setString(1,name);
                   pst.setString(2,price);
                   pst.setString(3,qty);
                   pst.executeUpdate();
                   JOptionPane.showMessageDialog(null,"recode added..");

                   txtname.setText("");
                   txtprice.setText("");
                   txtqty.setText("");
                   txtname.requestFocus();

               }
               catch (SQLException e1) {

                   e1.printStackTrace();
               }
            }
        });

// store the data and then display the data.
        searchbutton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                try{
                    String pid = txtpid.getText();

                    pst = con.prepareStatement("select pname,price,qty from products where pid = ?");
                    pst.setString(1,pid);
                    ResultSet rs = pst.executeQuery();

                    if(rs.next()==true)
                    {
                        String name = rs.getString(1);
                        String price = rs.getString(2);
                        String qty = rs.getString(3);

                        txtname.setText(name);
                        txtprice.setText(price);
                        txtqty.setText(qty);
                    }

                    else{
                        txtname.setText("");
                        txtprice.setText("");
                        txtqty.setText("");
                        JOptionPane.showMessageDialog(null,"Invalid ID");
                    }

                }
                catch (SQLException ex){
                    ex.printStackTrace();
                }

            }
        });

        // update the store data

        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String pid,name,price,qty;

                name = txtname.getText();
                price = txtprice.getText();
                qty = txtqty.getText();
                pid = txtpid.getText();

                try{
                    pst = con.prepareStatement("update products set pname = ?,price = ? ,qty = ? where pid = ?");
                    pst.setString(1,name);
                    pst.setString(2,price);
                    pst.setString(3,qty);
                    pst.setString(4,pid);

                    pst.executeUpdate();
                    JOptionPane.showMessageDialog(null,"Recode Update...");


                    txtname.setText("");
                    txtprice.setText("");
                    txtqty.setText("");
                    txtname.requestFocus();
                    txtpid.setText("");


                }
            catch (SQLException e1){

                    e1.printStackTrace();
            }

            }
        });

        // delect the stor data..

        delectButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String bid;

                bid = txtpid.getText();

                try{
                    pst = con.prepareStatement("delete from products where pid = ?");
                    pst.setString(1,bid);
                    pst.executeUpdate();
                    JOptionPane.showMessageDialog(null,"Recode delected");

                    txtname.setText("");
                    txtprice.setText("");
                    txtqty.setText("");
                    txtname.requestFocus();
                    txtpid.setText("");

                }
                catch (SQLException e1){
                    e1.printStackTrace();
                }






            }


        });
    }

    // connect to the data base.
    public void Connect(){

        try{

            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost/gbproducts","root","");
             System.out.println("Success");
        }
        catch (ClassNotFoundException ex)
        {
            ex.printStackTrace();
        }
        catch (SQLException ex)
        {
            ex.printStackTrace();
        }
    }





}
