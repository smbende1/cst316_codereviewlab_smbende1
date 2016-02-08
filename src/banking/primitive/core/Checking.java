package banking.primitive.core;

public class Checking extends Account {

	private static final long serialVersionUID = 11L;
	private int numWithdraws = 0;
	
	private Checking(String name) {
		super(name);
	}

    public static Checking createChecking(String name) {
        return new Checking(name);
    }

	public Checking(String name, float balance) {
		super(name, balance);
	}

	/**
	 * A deposit may be made unless the Checking account is closed
	 * @param float is the deposit amount
	 */
	public boolean deposit(float amount) {
		if (getState() != State.CLOSED && amount > 0.0f) {
			balance = balance + amount;
			if (balance >= 0.0f) {
				setState(State.OPEN);
			}
			return true;
		}
		return false;
	}

	/**
	 * Withdrawal. After 10 withdrawals a fee of $2 is charged per transaction You may 
	 * continue to withdraw an overdrawn account until the balance is below -$100
	 */
	public boolean withdraw(float amount) {
		//Check first for scenarios that would cause the withdraw to fail:
		//1. The amount is less than or equal to zero
		//2. The balance is positive but the withdraw would cause the account balance to go below -100
		//3. The balance is negative but the withdraw would cause the account balance to go below -100
		if ((amount <= 0.0f) || ((amount > balance + 100f) && (balance >= 0.0f)) || ((balance < 0.0f) && (amount > 100 - Math.abs(balance))))
		{
			return false;
		}
		else
		{
			balance = balance - amount;
			numWithdraws++;
			if (numWithdraws > 10)
				balance = balance - 2.0f;
			if (balance < 0.0f) {
				setState(State.OVERDRAWN);
			}
			return true;
		}
	}

	public String getType() { return "Checking"; }
	
	public String toString() {
		return "Checking: " + getName() + ": " + getBalance();
	}
}
