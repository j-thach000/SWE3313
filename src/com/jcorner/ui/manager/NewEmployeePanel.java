package com.jcorner.ui.manager;

import com.jcorner.model.Employee;
import com.jcorner.model.Role;
import com.jcorner.service.AuthService;
import com.jcorner.service.EmployeeService;
import com.jcorner.ui.AppFrame;
import com.jcorner.ui.HeaderBar;
import com.jcorner.ui.UIStyle;

import javax.swing.*;
import java.awt.*;

public class NewEmployeePanel extends JPanel {

    private final JTextField firstName = new JTextField(20);
    private final JTextField lastName  = new JTextField(20);
    private final JTextField email     = new JTextField(20);
    private final JTextField address   = new JTextField(20);
    private final JPasswordField password = new JPasswordField(20);
    private final JComboBox<Role> roleBox = new JComboBox<>(Role.values());

    public NewEmployeePanel() {
        setLayout(new BorderLayout());
        setBackground(UIStyle.BG);
        add(new HeaderBar("NEW EMPLOYEE", () ->
                AppFrame.instance().navigate(new ManageEmployeesPanel())), BorderLayout.NORTH);

        JPanel form = UIStyle.panel(new GridBagLayout());
        form.setBorder(UIStyle.padding(30));
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(6, 8, 6, 8);
        c.anchor = GridBagConstraints.WEST;

        int row = 0;
        row = addRow(form, c, row, "FIRST NAME:", firstName);
        row = addRow(form, c, row, "LAST NAME:",  lastName);
        row = addRow(form, c, row, "EMAIL:",      email);
        row = addRow(form, c, row, "ADDRESS:",    address);
        row = addRow(form, c, row, "PASSWORD:",   password);
        row = addRow(form, c, row, "ROLE:",       roleBox);

        c.gridx = 1; c.gridy = row;
        JButton save = UIStyle.button("CREATE EMPLOYEE");
        save.addActionListener(e -> create());
        form.add(save, c);

        add(form, BorderLayout.CENTER);
    }

    private int addRow(JPanel form, GridBagConstraints c, int row, String label, JComponent comp) {
        c.gridx = 0; c.gridy = row;
        form.add(UIStyle.label(label, UIStyle.HEADER), c);
        c.gridx = 1;
        form.add(comp, c);
        return row + 1;
    }

    private void create() {
        String fn = firstName.getText().trim();
        String ln = lastName.getText().trim();
        String pw = new String(password.getPassword());
        if (fn.isEmpty() || ln.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Name is required.");
            return;
        }
        if (!AuthService.isPasswordAcceptable(pw)) {
            JOptionPane.showMessageDialog(this,
                    "Password rejected: cannot be trivial (e.g. 1111 or 123456).");
            return;
        }
        Employee e = EmployeeService.create(fn, ln, email.getText().trim(),
                address.getText().trim(), (Role) roleBox.getSelectedItem(), pw);
        JOptionPane.showMessageDialog(this,
                "Employee created.\nID: " + e.getEmployeeID());
        AppFrame.instance().navigate(new ManageEmployeesPanel());
    }
}
