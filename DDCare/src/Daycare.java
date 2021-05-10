
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import com.mysql.jdbc.Statement;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.awt.event.ActionEvent;
import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.UIManager;
import java.awt.Font;

public class Daycare {

	private JFrame frame;
	private JTable table;
	private JTextField textFName;
	private JTextField textLName;
	private JTextField textDogName;
	private JTextField textBreed;

	/**
	 * @throws SQLException
	 * @wbp.parser.entryPoint
	 */
	public static void main(String[] args) throws SQLException {
		System.out.println("hello");
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Daycare window = new Daycare();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 * 
	 * 
	 * @wbp.parser.entryPoint
	 */
	public Daycare() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 * 
	 * @throws SQLException
	 * @wbp.parser.entryPoint
	 */
	private void initialize() {

		frame = new JFrame();
		frame.getContentPane().setBackground(UIManager.getColor("Button.shadow"));
		frame.setBounds(100, 100, 640, 472);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(228, 16, 386, 411);
		frame.getContentPane().add(scrollPane);

		table = new JTable();
		table.setBackground(UIManager.getColor("Table.darkShadow"));
		table.setAutoCreateRowSorter(true);
		scrollPane.setViewportView(table);

		// Name columns and set into model, set the model into the table
		Object[] columns = { "ID", "First Name", "Last Name", "Dog name", "Dog Breed" };
		DefaultTableModel model = new DefaultTableModel();
		model.setColumnIdentifiers(columns);
		table.setModel(model);

		textFName = new JTextField();
		textFName.setBounds(73, 38, 86, 20);
		frame.getContentPane().add(textFName);
		textFName.setColumns(10);

		textLName = new JTextField();
		textLName.setColumns(10);
		textLName.setBounds(73, 94, 86, 20);
		frame.getContentPane().add(textLName);

		textDogName = new JTextField();
		textDogName.setColumns(10);
		textDogName.setBounds(73, 150, 86, 20);
		frame.getContentPane().add(textDogName);

		textBreed = new JTextField();
		textBreed.setColumns(10);
		textBreed.setBounds(73, 206, 86, 20);
		frame.getContentPane().add(textBreed);

		JLabel lblFName = new JLabel("First Name");
		lblFName.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		lblFName.setForeground(Color.DARK_GRAY);
		lblFName.setBounds(73, 11, 86, 23);
		frame.getContentPane().add(lblFName);

		JLabel lblLName = new JLabel("Last Name");
		lblLName.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		lblLName.setForeground(Color.DARK_GRAY);
		lblLName.setBounds(73, 69, 86, 20);
		frame.getContentPane().add(lblLName);

		JLabel lblDogName = new JLabel("Dog Name");
		lblDogName.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		lblDogName.setForeground(Color.DARK_GRAY);
		lblDogName.setBounds(73, 125, 86, 20);
		frame.getContentPane().add(lblDogName);

		JLabel lblDogBreed = new JLabel("Dog Breed");
		lblDogBreed.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		lblDogBreed.setForeground(Color.DARK_GRAY);
		lblDogBreed.setBounds(73, 181, 86, 20);
		frame.getContentPane().add(lblDogBreed);

		JButton btnAddDog = new JButton("Add Dog");

		// Add a dog by using input text into the database
		btnAddDog.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String fName, lName, dogName, dogBreed;

				try {

					Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/daycare", "root",
							"Kt1Gks0T66FY3HLxZ4BR");

					if (conn != null) {
						System.out.println("Connected successfully");

						fName = textFName.getText();
						lName = textLName.getText();
						dogName = textDogName.getText();
						dogBreed = textBreed.getText();

						// Prepared statement, good against SQL injections
						PreparedStatement sql = conn.prepareStatement(
								"INSERT INTO daycare (fname, lname, dogname, dogbreed) VALUES (?,?,?,?)");

						sql.setString(1, fName);
						sql.setString(2, lName);
						sql.setString(3, dogName);
						sql.setString(4, dogBreed);

						sql.execute();

						System.out.println("Pet inserted!");
					}
				} catch (Exception e) {
					System.out.println("Not connected to a database or there has been an error");
				}

			}
		});
		btnAddDog.setBounds(70, 237, 89, 23);
		frame.getContentPane().add(btnAddDog);

		JLabel lblRemoveADog = new JLabel("Remove Clicked Dog");
		lblRemoveADog.setForeground(Color.DARK_GRAY);
		lblRemoveADog.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		lblRemoveADog.setBounds(20, 366, 138, 20);
		frame.getContentPane().add(lblRemoveADog);

		JButton btnRemove = new JButton("Remove");

		// Button to remove dog using the selected row ID to delete the data with that
		// ID in the database
		btnRemove.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int i = table.getSelectedRow();
				String id = table.getValueAt(i, 0).toString();

				if (i >= 0) {

					try {

						Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/daycare", "root",
								"Kt1Gks0T66FY3HLxZ4BR");

						String sql = "DELETE FROM daycare WHERE id=?";
						PreparedStatement ps = conn.prepareStatement(sql);
						ps.setString(1, id);

						ps.execute();

						System.out.println("Pet removed!");
					} catch (Exception e) {

					}
				} else {

					JOptionPane.showMessageDialog(frame, "Please select a row to delete");
				}
			}
		});
		btnRemove.setBounds(20, 397, 89, 23);
		frame.getContentPane().add(btnRemove);

		JLabel lblViewDogs = new JLabel("View Dogs");
		lblViewDogs.setForeground(Color.DARK_GRAY);
		lblViewDogs.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		lblViewDogs.setBounds(20, 302, 138, 20);
		frame.getContentPane().add(lblViewDogs);

		JButton btnView = new JButton("View");

		// View all current dogs by looping through a result set (returns everything
		// from a database)
		btnView.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				model.setRowCount(0);

				String query = "SELECT * FROM daycare";

				try {

					Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/daycare", "root",
							"Kt1Gks0T66FY3HLxZ4BR");

					Statement stmt = (Statement) conn.createStatement();
					ResultSet rs = stmt.executeQuery(query);

					while (rs.next()) {

						String a = rs.getString("id");
						String b = rs.getString("fname");
						String c = rs.getString("lname");
						String d = rs.getString("dogname");
						String e = rs.getString("dogbreed");

						model.addRow(new Object[] { a, b, c, d, e });

						table.setModel(model);

					}
				} catch (Exception e) {
					System.out.println("Not connected to a database or there has been an error");
				}
			}
		});
		btnView.setBounds(20, 333, 89, 23);
		frame.getContentPane().add(btnView);
	}
}