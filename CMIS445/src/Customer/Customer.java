package Customer;


public class Customer {

  private String lastName;
  private String firstName;
  private String SSN;
  private String email;
  private String phone;
  private String street;
  private String city;
  private String state;
  private String zip;
  private int accountID;
  private String pin;
  private double checkingBalance;
  private double savingsBalance;

  @Override
  public String toString() {
    final StringBuilder sb = new StringBuilder("Customer{");
    sb.append("lastName='").append(lastName).append('\'');
    sb.append(", firstName='").append(firstName).append('\'');
    sb.append(", SSN='").append(SSN).append('\'');
    sb.append(", email='").append(email).append('\'');
    sb.append(", phone='").append(phone).append('\'');
    sb.append(", street='").append(street).append('\'');
    sb.append(", city='").append(city).append('\'');
    sb.append(", state='").append(state).append('\'');
    sb.append(", zip='").append(zip).append('\'');
    sb.append(", accountID=").append(accountID);
    sb.append(", pin='").append(pin).append('\'');
    sb.append(", checkingBalance=").append(checkingBalance);
    sb.append(", savingsBalance=").append(savingsBalance);
    sb.append('}');
    return sb.toString();
  }

  public Customer() {}

  public String getSSN() { return SSN; }

  public void setSSN(String SSN) { this.SSN = SSN; }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getPhone() {
    return phone;
  }

  public void setPhone(String phone) {
    this.phone = phone;
  }

  public String getStreet() {
    return street;
  }

  public void setStreet(String street) {
    this.street = street;
  }

  public String getCity() {
    return city;
  }

  public void setCity(String city) {
    this.city = city;
  }

  public String getState() {
    return state;
  }

  public void setState(String state) {
    this.state = state;
  }

  public String getZip() {
    return zip;
  }

  public void setZip(String zip) {
    this.zip = zip;
  }

  public int getAccountID() {
    return accountID;
  }

  public void setAccountID(int accountID) {
    this.accountID = accountID;
  }

  public String getPin() {
    return pin;
  }

  public void setPin(String pin) {
    this.pin = pin;
  }

  public double getCheckingBalance() {
    return checkingBalance;
  }

  public void setCheckingBalance(double checkingBalance) {
    this.checkingBalance = checkingBalance;
  }

  public double getSavingsBalance() {
    return savingsBalance;
  }

  public void setSavingsBalance(double savingsBalance) {
    this.savingsBalance = savingsBalance;
  }
}
