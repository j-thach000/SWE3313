package com.jcorner.ui;

import com.jcorner.service.AuthService;
import com.jcorner.ui.busboy.BusboyDashboardPanel;
import com.jcorner.ui.cook.CookDashboardPanel;
import com.jcorner.ui.manager.ManagerDashboardPanel;
import com.jcorner.ui.waiter.WaiterDashboardPanel;

import javax.swing.*;
import java.awt.*;

public class LoginPanel extends JPanel {

    private final JTextField idField = new JTextField(16);
    private final JPasswordField pwField = new JPasswordField(16);
    private final JLabel error = UIStyle.label(" ", UIStyle.BODY);

    public LoginPanel() {
        setLayout(new BorderLayout());
        setBackground(UIStyle.BG);

        JPanel top = UIStyle.panel();
        top.setLayout(new BoxLayout(top, BoxLayout.Y_AXIS));
        top.setBorder(BorderFactory.createEmptyBorder(60, 0, 40, 0));
        JLabel title = UIStyle.label("J'S CORNER RESTAURANT", UIStyle.TITLE);
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        JLabel sub = UIStyle.label("EMPLOYEE LOGIN", UIStyle.HEADER);
        sub.setAlignmentX(Component.CENTER_ALIGNMENT);
        top.add(title);
        top.add(Box.createVerticalStrut(8));
        top.add(sub);
        add(top, BorderLayout.NORTH);

        JPanel form = UIStyle.panel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(8, 8, 8, 8);
        c.anchor = GridBagConstraints.WEST;

        c.gridx = 0; c.gridy = 0;
        form.add(UIStyle.label("LOGIN ID:", UIStyle.HEADER), c);
        c.gridx = 1;
        form.add(idField, c);

        c.gridx = 0; c.gridy = 1;
        form.add(UIStyle.label("PASSWORD:", UIStyle.HEADER), c);
        c.gridx = 1;
        form.add(pwField, c);

        c.gridx = 1; c.gridy = 2;
        JButton login = UIStyle.button("LOG IN");
        login.addActionListener(e -> attemptLogin());
        form.add(login, c);

        c.gridx = 0; c.gridy = 3; c.gridwidth = 2;
        error.setForeground(UIStyle.ERROR);
        form.add(error, c);

        add(form, BorderLayout.CENTER);

        JPanel hint = UIStyle.panel();
        hint.setLayout(new BoxLayout(hint, BoxLayout.Y_AXIS));
        hint.setBorder(BorderFactory.createEmptyBorder(0, 0, 30, 0));
        JLabel hintTitle = UIStyle.label("Demo accounts (password = 'pass' + ID digits)", UIStyle.BODY);
        hintTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        JLabel hintRow = UIStyle.label("Manager: MG1001 / pass1001  -  Waiter: WT2001 / pass2001  -  Cook: CK3001 / pass3001  -  Busboy: BB4001 / pass4001",
                UIStyle.BODY);
        hintRow.setAlignmentX(Component.CENTER_ALIGNMENT);
        hint.add(hintTitle);
        hint.add(Box.createVerticalStrut(4));
        hint.add(hintRow);
        add(hint, BorderLayout.SOUTH);

        // Press Enter to submit
        getInputMap(WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("ENTER"), "login");
        getActionMap().put("login", new AbstractAction() {
            @Override public void actionPerformed(java.awt.event.ActionEvent e) { attemptLogin(); }
        });
    }

    private void attemptLogin() {
        String id = idField.getText().trim();
        String pw = new String(pwField.getPassword());
        AuthService.Result r = AuthService.login(id, pw);
        switch (r) {
            case OK -> {
                switch (com.jcorner.service.Session.get().getCurrentUser().getRole()) {
                    case WAITER  -> AppFrame.instance().navigate(new WaiterDashboardPanel());
                    case COOK    -> AppFrame.instance().navigate(new CookDashboardPanel());
                    case BUSBOY  -> AppFrame.instance().navigate(new BusboyDashboardPanel());
                    case MANAGER -> AppFrame.instance().navigate(new ManagerDashboardPanel());
                }
            }
            case BAD_ID -> error.setText("ERROR: WRONG CREDENTIALS");
            case BAD_PASSWORD -> {
                int remaining = AuthService.remainingAttempts(id);
                error.setText("ERROR: WRONG CREDENTIALS - " + remaining + " more attempt(s) before lockout");
            }
            case LOCKED -> error.setText("ERROR: ACCOUNT LOCKED - see manager");
        }
        pwField.setText("");
    }
}
