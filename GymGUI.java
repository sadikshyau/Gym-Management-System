import java.util.ArrayList;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.File;
import java.io.BufferedReader;
import java.io.FileReader;

public class GymGUI extends JFrame {
    // ArrayList to store gym members
    private ArrayList<GymMember> members;

    // GUI components
    private JTextField txtId, txtName, txtLocation, txtPhone, txtEmail;
    private JComboBox<String> cbDay, cbMonth, cbYear;
    private JComboBox<String> cbStartDateDay, cbStartDateMonth, cbStartDateYear;
    private JTextField txtReferral, txtPaidAmount, txtRemovalReason, txtTrainer;
    private JTextField txtRegularPrice, txtPremiumPrice, txtDiscountAmount;
    private JRadioButton rbMale, rbFemale;
    private ButtonGroup bgGender;
    private JComboBox<RegularMember.Plan> cbPlan;

    // Constructor
    public GymGUI() {
        members = new ArrayList<>();

        // Set up the frame
        setTitle("Gym Management System");
        setSize(900, 650);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Set background color for the frame
        ImageIcon backgroundImage = new ImageIcon("C:/Users/andel/Downloads/download (1).jpeg"); // Use your actual image path
        JLabel backgroundLabel = new JLabel(backgroundImage);
        backgroundLabel.setLayout(new BorderLayout());
        setContentPane(backgroundLabel);
        

        JPanel inputPanel = new JPanel(new GridLayout(30, 30));
        inputPanel.setBackground(new Color(0, 0, 0));

        // Create main panel with GridBagLayout
        JPanel mainPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        // Create form panel
        JPanel formPanel = createFormPanel();

        // Create button panel
        JPanel buttonPanel = createButtonPanel();

        // Add panels to main panel
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.BOTH;
        mainPanel.add(formPanel, gbc);

        gbc.gridy = 1;
        mainPanel.add(buttonPanel, gbc);

        // Add main panel to frame
        add(mainPanel);
        mainPanel.setOpaque(false);
        formPanel.setOpaque(false);
        buttonPanel.setOpaque(false);

        setVisible(true);
    }


    // Create form panel with input fields
    private JPanel createFormPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setOpaque(false);
        panel.setBorder(BorderFactory.createTitledBorder("Member Information"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;

        // Initialize input fields
        txtId = new JTextField(15);
        txtName = new JTextField(15);
        txtLocation = new JTextField(15);
        txtPhone = new JTextField(15);
        txtEmail = new JTextField(15);
        txtReferral = new JTextField(15);
        txtPaidAmount = new JTextField(15);
        txtRemovalReason = new JTextField(15);
        txtTrainer = new JTextField(15);

        // Initialize non-editable fields
        txtRegularPrice = new JTextField(15);
        txtRegularPrice.setEditable(false);
        txtPremiumPrice = new JTextField("5000");
        txtPremiumPrice.setEditable(false);
        txtDiscountAmount = new JTextField("0");
        txtDiscountAmount.setEditable(false);

        // Initialize radio buttons for gender
        rbMale = new JRadioButton("Male");
        rbFemale = new JRadioButton("Female");
        bgGender = new ButtonGroup();
        bgGender.add(rbMale);
        bgGender.add(rbFemale);
        JPanel genderPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        genderPanel.add(rbMale);
        genderPanel.add(rbFemale);

        // Initialize combo box for plan
        cbPlan = new JComboBox<>(RegularMember.Plan.values());
        cbPlan.addActionListener(e -> {
            RegularMember.Plan selectedPlan = (RegularMember.Plan) cbPlan.getSelectedItem();
            txtRegularPrice.setText(String.valueOf(selectedPlan.getPrice()));
            panel.setBackground(new Color(255, 255, 204));
        });

        // Set initial value for regular price
        txtRegularPrice.setText(String.valueOf(RegularMember.Plan.BASIC.getPrice()));

        // Initialize combo boxes for DOB
        String[] days = new String[31];
        for (int i = 0; i < 31; i++) {
            days[i] = String.valueOf(i + 1);
        }
        cbDay = new JComboBox<>(days);

        String[] months = {"01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12"};
        cbMonth = new JComboBox<>(months);

        String[] years = new String[100];
        for (int i = 0; i < 100; i++) {
            years[i] = String.valueOf(1920 + i);
        }
        cbYear = new JComboBox<>(years);

        // Initialize combo boxes for Membership Start Date
        cbStartDateDay = new JComboBox<>(days);
        cbStartDateMonth = new JComboBox<>(months);
        cbStartDateYear = new JComboBox<>(years);

        // Add components to panel with labels
        int row = 0;

        addLabelAndField(panel, "Member ID:", txtId, gbc, row++);
        addLabelAndField(panel, "Name:", txtName, gbc, row++);
        addLabelAndField(panel, "Location:", txtLocation, gbc, row++);
        addLabelAndField(panel, "Phone:", txtPhone, gbc, row++);
        addLabelAndField(panel, "Email:", txtEmail, gbc, row++);

        // Add gender radio buttons
        gbc.gridx = 0;
        gbc.gridy = row;
        panel.add(new JLabel("Gender:"), gbc);
        gbc.gridx = 1;
        panel.add(genderPanel, gbc);
        row++;

        // Add DOB combo boxes
        gbc.gridx = 0;
        gbc.gridy = row;
        panel.add(new JLabel("Date of Birth:"), gbc);
        gbc.gridx = 1;
        JPanel dobPanel = new JPanel();
        dobPanel.add(cbDay);
        dobPanel.add(cbMonth);
        dobPanel.add(cbYear);
        panel.add(dobPanel, gbc);
        row++;

        // Add Membership Start Date combo boxes
        gbc.gridx = 0;
        gbc.gridy = row;
        panel.add(new JLabel("Membership Start Date:"), gbc);
        gbc.gridx = 1;
        JPanel startDatePanel = new JPanel();
        startDatePanel.add(cbStartDateDay);
        startDatePanel.add(cbStartDateMonth);
        startDatePanel.add(cbStartDateYear);
        panel.add(startDatePanel, gbc);
        row++;

        addLabelAndField(panel, "Referral Source:", txtReferral, gbc, row++);
        addLabelAndField(panel, "Plan:", cbPlan, gbc, row++);
        addLabelAndField(panel, "Regular Plan Price:", txtRegularPrice, gbc, row++);
        addLabelAndField(panel, "Premium Plan Price:", txtPremiumPrice, gbc, row++);
        addLabelAndField(panel, "Paid Amount:", txtPaidAmount, gbc, row++);
        addLabelAndField(panel, "Discount Amount:", txtDiscountAmount, gbc, row++);
        addLabelAndField(panel, "Removal Reason:", txtRemovalReason, gbc, row++);
        addLabelAndField(panel, "Trainer's Name:", txtTrainer, gbc, row++);

        return panel;
    }

    // Helper method to add label and field to panel
    private void addLabelAndField(JPanel panel, String labelText, JTextField field, GridBagConstraints gbc, int row) {
        gbc.gridx = 0;
        gbc.gridy = row;
        panel.add(new JLabel(labelText), gbc);
        gbc.gridx = 1;
        panel.add(field, gbc);
    }

    private void addLabelAndField(JPanel panel, String labelText, JComboBox<?> comboBox, GridBagConstraints gbc, int row) {
        gbc.gridx = 0;
        gbc.gridy = row;
        panel.add(new JLabel(labelText), gbc);
        gbc.gridx = 1;
        panel.add(comboBox, gbc);
    }

    // Create button panel with all action buttons
    private JPanel createButtonPanel() {
        JPanel panel = new JPanel(new GridLayout(0, 3, 10, 10));
        panel.setOpaque(false);
        panel.setBorder(BorderFactory.createTitledBorder("Actions"));

        // Create buttons
        JButton btnAddRegular = new JButton("Add Regular Member");
        JButton btnAddPremium = new JButton("Add Premium Member");
        JButton btnActivate = new JButton("Activate Membership");
        JButton btnDeactivate = new JButton("Deactivate Membership");
        JButton btnMarkAttendance = new JButton("Mark Attendance");
        JButton btnUpgradePlan = new JButton("Upgrade Plan");
        JButton btnCalculateDiscount = new JButton("Calculate Discount");
        JButton btnRevertRegular = new JButton("Revert Regular Member");
        JButton btnRevertPremium = new JButton("Revert Premium Member");
        JButton btnPayDue = new JButton("Pay Due Amount");
        JButton btnDisplay = new JButton("Display Members");
        JButton btnClear = new JButton("Clear Fields");
        JButton btnSaveToFile = new JButton("Save to File");
        JButton btnReadFromFile = new JButton("Read from File");

        // Add action listeners to buttons
        btnAddRegular.addActionListener(e -> addRegularMember());
        btnAddPremium.addActionListener(e -> addPremiumMember());
        btnActivate.addActionListener(e -> activateMembership());
        btnDeactivate.addActionListener(e -> deactivateMembership());
        btnMarkAttendance.addActionListener(e -> markAttendance());
        btnUpgradePlan.addActionListener(e -> upgradePlan());
        btnCalculateDiscount.addActionListener(e -> calculateDiscount());
        btnRevertRegular.addActionListener(e -> revertRegularMember());
        btnRevertPremium.addActionListener(e -> revertPremiumMember());
        btnPayDue.addActionListener(e -> payDueAmount());
        btnDisplay.addActionListener(e -> displayMembers());
        btnClear.addActionListener(e -> clearFields());
        btnSaveToFile.addActionListener(e -> saveToFile());
        btnReadFromFile.addActionListener(e -> readFromFile());

        // Add buttons to panel
        panel.add(btnAddRegular);
        panel.add(btnAddPremium);
        panel.add(btnActivate);
        panel.add(btnDeactivate);
        panel.add(btnMarkAttendance);
        panel.add(btnUpgradePlan);
        panel.add(btnCalculateDiscount);
        panel.add(btnRevertRegular);
        panel.add(btnRevertPremium);
        panel.add(btnPayDue);
        panel.add(btnDisplay);
        panel.add(btnClear);
        panel.add(btnSaveToFile);
        panel.add(btnReadFromFile);

        panel.setVisible(true);
        return panel;
    }

    // Method to add a regular member
    public void addRegularMember() {
        try {
            String memberId = txtId.getText().trim();
            String name = txtName.getText().trim();
            String location = txtLocation.getText().trim();
            String phone = txtPhone.getText().trim();
            String email = txtEmail.getText().trim();
            String gender = rbMale.isSelected() ? "Male" : (rbFemale.isSelected() ? "Female" : "");
            if (gender.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please select a gender!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            LocalDate dob = LocalDate.of(
                    Integer.parseInt(cbYear.getSelectedItem().toString()),
                    Integer.parseInt(cbMonth.getSelectedItem().toString()),
                    Integer.parseInt(cbDay.getSelectedItem().toString())
            );
            LocalDate startDate = LocalDate.of(
                    Integer.parseInt(cbStartDateYear.getSelectedItem().toString()),
                    Integer.parseInt(cbStartDateMonth.getSelectedItem().toString()),
                    Integer.parseInt(cbStartDateDay.getSelectedItem().toString())
            );
            String referral = txtReferral.getText().trim();
            RegularMember.Plan plan = (RegularMember.Plan) cbPlan.getSelectedItem();
            double paidAmount = Double.parseDouble(txtPaidAmount.getText().trim());

            RegularMember member = new RegularMember(memberId, name, location, phone, email,
                    gender, dob, startDate, referral, plan, paidAmount);

            members.add(member);
            JOptionPane.showMessageDialog(this, "Regular member added successfully!");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error adding member: " + e.getMessage());
        }
    }

    // Method to add a premium member
    private void addPremiumMember() {
        try {
            String memberId = txtId.getText().trim();

            // Check if member ID already exists
            if (findMemberById(memberId) != null){
                JOptionPane.showMessageDialog(this, "Member ID already exists!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            String name = txtName.getText().trim();
            String location = txtLocation.getText().trim();
            String phone = txtPhone.getText().trim();
            String email = txtEmail.getText().trim();
            String gender = rbMale.isSelected() ? "Male" : (rbFemale.isSelected() ? "Female" : "");
            if (gender.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please select a gender!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            LocalDate dob = LocalDate.of(
                    Integer.parseInt(cbYear.getSelectedItem().toString()),
                    Integer.parseInt(cbMonth.getSelectedItem().toString()),
                    Integer.parseInt(cbDay.getSelectedItem().toString())
            );
            LocalDate startDate = LocalDate.of(
                    Integer.parseInt(cbStartDateYear.getSelectedItem().toString()),
                    Integer.parseInt(cbStartDateMonth.getSelectedItem().toString()),
                    Integer.parseInt(cbStartDateDay.getSelectedItem().toString())
            );
            String referral = txtReferral.getText().trim();
            String trainer = txtTrainer.getText().trim();

            PremiumMember member = new PremiumMember(memberId, name, location, phone, email, gender,
                    dob, startDate, referral, trainer);
            members.add(member);
            JOptionPane.showMessageDialog(this, "Premium member added successfully!");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Invalid date format! Use yyyy-MM-dd", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Method to activate membership
    private void activateMembership() {
        String memberId = txtId.getText();
        GymMember member = findMemberById(memberId);

        if (member != null) {
            member.activateMembership();
            JOptionPane.showMessageDialog(this, "Membership activated successfully!");
        } else {
            JOptionPane.showMessageDialog(this, "Member not found!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Method to deactivate membership
    private void deactivateMembership() {
        String memberId = txtId.getText();
        GymMember member = findMemberById(memberId);

        if (member != null) {
            member.deactivateMembership();
            JOptionPane.showMessageDialog(this, "Membership deactivated successfully!");
        } else {
            JOptionPane.showMessageDialog(this, "Member not found!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Method to mark attendance
    private void markAttendance() {
        try {
            String memberId = txtId.getText();
            LocalDate attendanceDate = LocalDate.of(
                    Integer.parseInt(cbStartDateYear.getSelectedItem().toString()),
                    Integer.parseInt(cbStartDateMonth.getSelectedItem().toString()),
                    Integer.parseInt(cbStartDateDay.getSelectedItem().toString())
            );

            GymMember member = findMemberById(memberId);
            if (member != null) {
                if (member.isActiveStatus()) {
                    member.markAttendance();
                    JOptionPane.showMessageDialog(this, "Attendance marked successfully for member ID: " + memberId);
                } else {
                    JOptionPane.showMessageDialog(this, "Cannot mark attendance for inactive member!", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(this, "Member not found!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Invalid date format! Use yyyy-MM-dd", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Method to upgrade plan
   private void upgradePlan() {
    String memberId = txtId.getText().trim();
    GymMember member = findMemberById(memberId);

    if (member instanceof RegularMember) {
        RegularMember regular = (RegularMember) member;
        RegularMember.Plan currentPlan = regular.getPlan();
        RegularMember.Plan[] plans = RegularMember.Plan.values();
        int currentIndex = currentPlan.ordinal();
        if (currentIndex < plans.length - 1) {
            regular.setPlan(plans[currentIndex + 1]);
            txtRegularPrice.setText(String.valueOf(plans[currentIndex + 1].getPrice()));
            JOptionPane.showMessageDialog(this, "Plan upgraded to " + plans[currentIndex + 1].name());
        } else {
            JOptionPane.showMessageDialog(this, "Already at highest plan!");
        }
    } else {
        JOptionPane.showMessageDialog(this, "Only regular members can upgrade plans!", "Error", JOptionPane.ERROR_MESSAGE);
    }
}


    // Method to calculate discount
    private void calculateDiscount() {
        String memberId = txtId.getText();
        GymMember member = findMemberById(memberId);

        if (member != null) {
            if (member instanceof PremiumMember) {
                PremiumMember premiumMember = (PremiumMember) member;
                premiumMember.calculateDiscount();
                txtDiscountAmount.setText(String.valueOf(premiumMember.getDiscountAmount()));
                JOptionPane.showMessageDialog(this, "Discount calculated successfully!");
            } else {
                JOptionPane.showMessageDialog(this, "This feature is only for Premium members!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Member not found!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Method to revert regular member
    private void revertRegularMember() {
        String memberId = txtId.getText();
        GymMember member = findMemberById(memberId);

        if (member != null) {
            if (member instanceof RegularMember) {
                RegularMember regularMember = (RegularMember) member;
                String reason = txtRemovalReason.getText();
                regularMember.revertMember(reason);
                JOptionPane.showMessageDialog(this, "Regular member reverted successfully!");
            } else {
                JOptionPane.showMessageDialog(this, "This member is not a Regular member!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Member not found!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Method to revert premium member
  private void revertPremiumMember() {
    String memberId = txtId.getText().trim();
    GymMember member = findMemberById(memberId);

    if (member instanceof PremiumMember) {
        members.remove(member);
        JOptionPane.showMessageDialog(this, "Premium member removed from list.");
    } else {
        JOptionPane.showMessageDialog(this, "This member is not a premium member!", "Error", JOptionPane.ERROR_MESSAGE);
    }
}


    // Method to pay due amount
   private void payDueAmount() {
    String memberId = txtId.getText().trim();
    GymMember member = findMemberById(memberId);

    if (member instanceof RegularMember) {
        RegularMember regular = (RegularMember) member;
        double due = regular.getPlan().getPrice() - regular.getPaidAmount();
        if (due > 0) {
            regular.setPaidAmount(regular.getPaidAmount() + due);
            txtPaidAmount.setText(String.valueOf(regular.getPaidAmount()));
            JOptionPane.showMessageDialog(this, "Due amount of " + due + " paid successfully.");
        } else {
            JOptionPane.showMessageDialog(this, "No due amount left to pay.");
        }
    } else {
        JOptionPane.showMessageDialog(this, "Only regular members have due amounts!", "Error", JOptionPane.ERROR_MESSAGE);
    }
}


    // Method to display all members
private void displayMembers() {
    if (members.isEmpty()) {
        JOptionPane.showMessageDialog(this, "No members to display!", "Information", JOptionPane.INFORMATION_MESSAGE);
        return;
    }

    // Create a new frame to display members
    JFrame displayFrame = new JFrame("Member List");
    displayFrame.setSize(800, 500);
    displayFrame.setLocationRelativeTo(this);

    // Create text area for display
    JTextArea textArea = new JTextArea();
    textArea.setEditable(false);
    textArea.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));

    // Add member information
    for (GymMember member : members) {
        String memberType = (member instanceof RegularMember) ? "Regular" : "Premium";
        textArea.append(member.display() + "\nMembership Type: " + memberType + "\n-----------------------------\n");
    }

    // Add text area inside a scroll pane
    JScrollPane scrollPane = new JScrollPane(textArea);

    displayFrame.add(scrollPane);
    displayFrame.setVisible(true);
}

    // Method to clear all input fields
    private void clearFields() {
        txtId.setText("");
        txtName.setText("");
        txtLocation.setText("");
        txtPhone.setText("");
        txtEmail.setText("");
        bgGender.clearSelection();
        cbDay.setSelectedIndex(0);
        cbMonth.setSelectedIndex(0);
        cbYear.setSelectedIndex(0);
        cbStartDateDay.setSelectedIndex(0);
        cbStartDateMonth.setSelectedIndex(0);
        cbStartDateYear.setSelectedIndex(0);
        txtReferral.setText("");
        txtPaidAmount.setText("");
        txtRemovalReason.setText("");
        txtTrainer.setText("");
        txtDiscountAmount.setText("0");
        cbPlan.setSelectedIndex(0);
    }

    // Method to save member data to file
    private void saveToFile() {
        try {
            if (members.isEmpty()) {
                JOptionPane.showMessageDialog(this, "No members to save!", "Information", JOptionPane.INFORMATION_MESSAGE);
                return;
            }

            FileWriter writer = new FileWriter("MemberDetails.txt");

            // header
            writer.write(String.format("%-5s %-15s %-15s %-15s %-25s %-20s %-10s %-10.2f %-10d %-15d %-10s %-15s %-15.2f %-15.2f\n",
                    "ID", "Name", "Location", "Phone", "Email", "Membership Start Date", "Plan",
                    "Price", "Attendance", "Loyalty Points", "Active Status", "Full Payment",
                    "Discount Amount", "Net Amount Paid"));
            writer.write("=".repeat(200) + "\n");

            // member information
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

            for (GymMember member : members) {
                if (member instanceof RegularMember) {
                    RegularMember rm = (RegularMember) member;
                    writer.write(String.format("%-5s %-15s %-15s %-15s %-25s %-20s %-10s %-10.2f %-10d %-15d %-10s %-15s %-15s %-15s\n",
                            rm.getMemberId(), rm.getName(), rm.getLocation(), rm.getPhone(), rm.getEmail(),
                            rm.getMembershipStartDate().format(formatter), rm.getPlan(),
                            rm.calculatePrice(), rm.getAttendanceCount(), rm.getLoyaltyPoints(),
                            rm.isActiveStatus() ? "Active" : "Inactive", "N/A", "N/A", rm.calculatePrice()));
                } else if (member instanceof PremiumMember) {
                    PremiumMember pm = (PremiumMember) member;
                    writer.write(String.format("%-5s %-15s %-15s %-15s %-25s %-20s %-10s %-10.2f %-10d %-15s %-10s %-15s %-15.2f %-15.2f\n",
                            pm.getMemberId(), pm.getName(), pm.getLocation(), pm.getPhone(), pm.getEmail(),
                            pm.getMembershipStartDate().format(formatter), "Premium",
                            pm.calculatePrice(), pm.getAttendanceCount(), "N/A",
                            pm.isActiveStatus() ? "Active" : "Inactive",
                            pm.isFullPayment() ? "Yes" : "No", pm.getDiscountAmount(), pm.getPaidAmount()));
                }
            
            }

            writer.close();
            JOptionPane.showMessageDialog(this, "Member details saved to MemberDetails.txt successfully!");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error saving to file: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    
    }

    // Method to read member data from file
    private void readFromFile() {
        try {
            File file = new File("MemberDetails.txt");
            if (!file.exists()) {
                JOptionPane.showMessageDialog(this, "File MemberDetails.txt does not exist!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Create a new frame to display file contents
            JFrame displayFrame = new JFrame("Member Details from File");
            displayFrame.setSize(800, 500);
            displayFrame.setLocationRelativeTo(this);

            // Create text area for display
            JTextArea textArea = new JTextArea();
            textArea.setEditable(false);
            textArea.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));

            // Read file contents
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line;
            while ((line = reader.readLine()) != null) {
                textArea.append(line + "\n");
            }
            reader.close();

            // Add text area to a scroll pane
            JScrollPane scrollPane = new JScrollPane(textArea);
            displayFrame.add(scrollPane);

            displayFrame.setVisible(true);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error reading from file: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Helper method to find a member by ID
  private GymMember findMemberById(String memberId) {
    for (GymMember member : members) {
        if (member.getMemberId().equalsIgnoreCase(memberId)) {
            return member;
        }
    }
    return null;
}
public static void main(String[] args) {
    SwingUtilities.invokeLater(() -> {
        new GymGUI(); // Directly launch GymGUI
    });
}
}
    

    


