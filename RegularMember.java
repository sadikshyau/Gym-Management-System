import java.time.LocalDate;

public class RegularMember extends GymMember {


    public enum Plan {
        BASIC(2000),
        STANDARD(4000),
        PREMIUM(8000);

        private final double price;

        Plan(double price) {
            this.price = price;
        }

        public double getPrice() {
            return price;
        }
    }

    private Plan plan;
    private double paidAmount;
    private int loyaltyPoints;

    private static final double DISCOUNT_RATE = 0.05;

    // Constructor
    public RegularMember(String memberId, String name, String location, String phone,
                         String email, String gender, LocalDate dob,
                         LocalDate membershipStartDate, String referralSource,
                         Plan plan, double paidAmount) {
        super(memberId, name, location, phone, email, gender, dob, membershipStartDate, referralSource);
        this.plan = plan;
        this.paidAmount = paidAmount;
        this.loyaltyPoints = 0;
    }
    
    public void revertMember(String reason) {
    this.setPlan(Plan.BASIC);
    this.setPaidAmount(0);
    this.setLoyaltyPoints(0);
    this.attendanceDates.clear();
    this.setActiveStatus(false);
    this.setRemovalReason(reason); // Only if removalReason is a field in GymMember
    System.out.println("Regular member " + name + " has been reverted for reason: " + reason);
}

    @Override
    public double calculatePrice() {
        double basePrice = plan.getPrice();

        if (getAttendanceCount() > 10) {
            basePrice -= basePrice * DISCOUNT_RATE;
        }

        return basePrice;
    }

    @Override
    public void markAttendance() {
        if (activeStatus) {
            LocalDate today = LocalDate.now();
            if (!attendanceDates.contains(today)) {
                attendanceDates.add(today);
                loyaltyPoints++;

                if (getAttendanceCount() == 11) {
                    loyaltyPoints += 5;
                }

                System.out.println("Attendance marked for " + name + " on " + today);
                System.out.println("Loyalty Points: " + loyaltyPoints);
            } else {
                System.out.println("Attendance already marked today for " + name);
            }
        } else {
            System.out.println("Cannot mark attendance for inactive member: " + name);
        }
    }
    
   public boolean upgradePlan(Plan newPlan) {
    if (newPlan.ordinal() > this.plan.ordinal()) {
        this.plan = newPlan;
        this.paidAmount = newPlan.getPrice();
        System.out.println("Plan upgraded to: " + newPlan.name());
        return true;
    } else {
        System.out.println("Selected plan is not higher than current.");
        return false;
    }
}

    // Getters and Setters
    public Plan getPlan() {
        return plan;
    }

    public void setPlan(Plan plan) {
        this.plan = plan;
    }

    public double getPaidAmount() {
        return paidAmount;
    }

    public void setPaidAmount(double paidAmount) {
        this.paidAmount = paidAmount;
    }

    public int getLoyaltyPoints() {
        return loyaltyPoints;
    }

    public void setLoyaltyPoints(int loyaltyPoints) {
        this.loyaltyPoints = loyaltyPoints;
    }

    @Override
    public String display() {
        return super.display()
                + "\nMembership Type: Regular"
                + "\nPlan: " + plan.name()
                + "\nPaid Amount: " + paidAmount
                + "\nCalculated Price: " + calculatePrice()
                + "\nAttendance Count: " + getAttendanceCount()
                + "\nLoyalty Points: " + loyaltyPoints
                + "\nActive Status: " + (activeStatus ? "Active" : "Inactive");
    }
}


