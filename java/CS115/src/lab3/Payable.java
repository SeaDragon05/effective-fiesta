package lab3;

public interface Payable {
	enum PayType {
		CASH,
		CARD,
		PHONE
	}
	abstract PayType getPayType();
	abstract void setPayType(PayType type);
}