package atm.service.impl;

import atm.dao.AccountDao;
import atm.model.UserAccount;
import atm.service.AccountService;

import java.util.Random;
import java.util.Scanner;

public class AccountServiceImpl implements AccountService {
    private final AccountDao accountDao = new AccountDao();
    private final Random random = new Random();
    private final Scanner scanner = new Scanner(System.in);
    UserAccount userAccount1 = new UserAccount();
  UserAccount frien=new UserAccount();

    @Override
    public void singUp(String name, String lastName) {
        try {

            userAccount1.setName(name);
            userAccount1.setLastName(lastName);
            String cardNumber = String.valueOf(random.nextInt(9999999, 99999999));
            String pinCode = String.valueOf(random.nextInt(999, 9999));
            userAccount1.setCardNumber(cardNumber);
            userAccount1.setPinCode(pinCode);

            accountDao.getUserAccounts().add(userAccount1);
        System.out.println(userAccount1);
        } catch (IllegalArgumentException a) {
            System.out.println("\u001B[31m" + "напишите правильно" + "\001B[0m");
        }
        //"Sign up" значит "Зарегестрироваться" для нового пользователя.

    }

    @Override
    public void singIn(String name, String lastName) {
        System.out.println("Добро пожаловать в банкомат");
        System.out.println("Напишите имя ");
        name = scanner.nextLine();
        System.out.println("Напишите фамилья");
        lastName = scanner.nextLine();
        String cardCode = String.valueOf(random.nextInt(9999999, 99999999));
        String pinCode = String.valueOf(random.nextInt(999, 9999));
        if (userAccount1.getName().equals(name) && userAccount1.getLastName().equals(lastName)) {
            while (true) {
                System.out.println("1 : Баланс\n" +
                        "2 :Депозит\n" +
                        "3 :Отправить деньги другу\n" +
                        "4: Снять деньги");
                System.out.println("введите выбор");
                int n = scanner.nextInt();
                switch (n) {
                    case 1:
                        balance(userAccount1.getCardNumber(), userAccount1.getPinCode());
                        break;
                    case 2: {
                        Scanner scanner1 = new Scanner(System.in);
                        System.out.println("напишите номер карты ");
                        String card = scanner1.nextLine();
                        System.out.println("напишите pin-код");
                        String pin = scanner1.nextLine();
                        deposit(card, pin);
                    }

                    break;
                    case 3:
                        System.out.println("напишите номер карты друга");
                        int summa = scanner.nextInt();
                        UserAccount userAccount = new UserAccount();
                        sendToFriend(userAccount.getCardNumber());
                        userAccount.setBalance(userAccount1.getBalance() - summa);
                        break;
                    case 4:
                        withdrawMoney(scanner.nextInt());
                        break;
                }
            }
        }
    }
//"Sign-in" - это виртуальный аналог того, как вы расписываетесь в журнале,
// приходя на работу. Буквально - "отметиться в системе".
// Отличие для "Sign in" может заключаться в том,
// что при входе в систему может регистрироваться время входа.
// Это может быть какая-нибудь временная регистрация или вход в запись на непродолжительный сеанс.


    @Override
    public void balance(String cardNumber, String pinCode) {
        if (cardNumber.equals(userAccount1.getCardNumber()) && pinCode.equals(userAccount1.getPinCode())) {
            System.out.println(userAccount1.getBalance());
        } else {
            System.out.println("неправильно номер карта или pin-кодд");
        }
    }

    @Override
    public void deposit(String cardNumber, String pinCode) {
        try {
            if (cardNumber.equals(userAccount1.getCardNumber()) && pinCode.equals(userAccount1.getPinCode())) {
                System.out.println("Сколько вы хотите внести на депозит");
                int d = scanner.nextInt();
                for (UserAccount userAccount : accountDao.getUserAccounts()) {
                    userAccount.setBalance(d + userAccount.getBalance());
                    System.out.println("ваш баланс : " + " " + userAccount.getBalance());
                }
            } else {
                System.out.println("неправильно номер карта или pin-код");
            }
        } catch (RuntimeException a) {
            System.out.println("напишите правильно номер карта или pin-code ");
        }
    }

    @Override
    public void sendToFriend(String friendCardNumber) {
        System.out.print("Введите номер своей карты: ");
        String num = scanner.nextLine();
        System.out.print("Ваш баланс: ");
        for (UserAccount userAccount : accountDao.getUserAccounts()) {
            if (userAccount.getCardNumber().equals(num)) {
                System.out.println(userAccount.getBalance());
            }
        }
        System.out.print("Введите сумму которую хотите отправить другу: ");
        int sum = scanner.nextInt();
        for (UserAccount us : accountDao.getUserAccounts()) {
            if (us.getCardNumber().equals(num)) {
                if (us.getBalance() >= sum) {
                    int balance = us.getBalance() - sum;
                    us.setBalance(balance);
                    System.out.println("Ваш баланс: " + us.getBalance());
                }
            }
        }
        for (UserAccount u : accountDao.getUserAccounts()) {
            if (friendCardNumber.equals(u.getCardNumber())) {
                int balance = u.getBalance() + sum;
                u.setBalance(balance);
                System.out.println("Баланс вашего друга: " + u.getBalance());
            }
        }



    }

    @Override
    public void withdrawMoney(int amount) {
        System.out.println("напишите сумма ");
        System.out.println(amount);
    }
}
