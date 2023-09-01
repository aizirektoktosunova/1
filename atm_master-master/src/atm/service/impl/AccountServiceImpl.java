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

    public enum Color {
        ANSI_RESET("\u001B[0m"),
        ANSI_RED("\u001B[31m"),
        ANSI_GREEN("\u001B[32m"),
        ;
        private final String color;

        Color(String color) {
            this.color = color;
        }

        @Override
        public String toString() {
            return color;
        }
    }

    @Override
    public void singUp(String name, String lastName) {
        UserAccount userAccount2 = new UserAccount();
        try {
            userAccount2.setName(name);
            userAccount2.setLastName(lastName);
            String cardNumber = String.valueOf(random.nextInt(9999999, 99999999));
            String pinCode = String.valueOf(random.nextInt(999, 9999));
            userAccount2.setCardNumber(cardNumber);
            userAccount2.setPinCode(pinCode);
            userAccount2.setBalance(0);
            accountDao.getUserAccounts().add(userAccount2);
            System.out.println(userAccount2);

        } catch (RuntimeException a) {
            System.out.println(Color.ANSI_RED + "напишите правильно!" + Color.ANSI_RESET);
        }
        //"Sign up" значит "Зарегестрироваться" для нового пользователя.

    }

    @Override
    public void singIn(String name, String lastName) {
        System.out.println("Добро пожаловать в банкомат");
//        System.out.println("Выберите язык\n" +
//                "1 : Кыргыз-тили\n" +
//                "2 : Русский-Язык\n" +
//                "3 : English language);
//        System.out.println("Введите выбор : ");
//        int num=scanner.nextInt();
//        switch (num){
//            case 1: {
//            }
//        case 2: {
//
//        }   case 3:{
//               English();
//            }
        System.out.println("Напишите имя ");
        name = scanner.nextLine();
        System.out.println("Напишите фамилия");
        lastName = scanner.nextLine();
        for (UserAccount userAccount1 : accountDao.getUserAccounts()) {
            userAccount1 = checkUser(userAccount1.getCardNumber(), userAccount1.getPinCode());
            if (name.equals(userAccount1.getName()) && lastName.equals(userAccount1.getLastName())) {
                while (true) {
                    try {
                        System.out.println("1-> : Баланс\n" +
                                "2-> : Депозит\n" +
                                "3-> : Отправить деньги другу\n" +
                                "4-> :  Снять деньги");
                        System.out.println("введите выбор");
                        int n = scanner.nextInt();
                        switch (n) {
                            case 1: {
                                System.out.println("напишите номер карты ");
                                String card = String.valueOf(scanner.nextInt());
                                System.out.println("напишите pin-код");
                                String pin = String.valueOf(scanner.nextInt());
                                balance(card, pin);
                            }
                            break;
                            case 2: {

                                System.out.println("напишите номер карты ");
                                String card = String.valueOf(scanner.nextInt());
                                System.out.println("напишите pin-код");
                                String pin = String.valueOf(scanner.nextInt());
                                deposit(card, pin);
                            }

                            break;
                            case 3: {
                                System.out.println("напишите номер карты друга");
                                String friendCardNumber = String.valueOf(scanner.nextInt());
                                sendToFriend(friendCardNumber);
                            }
                            break;
                            case 4:
                                System.out.println("Напишите сумма");
                                int sum = Integer.parseInt(String.valueOf(scanner.nextInt()));
                                withdrawMoney(sum);
                                break;
                        }
                        if (!name.equals(userAccount1.getName()) && !lastName.equals(userAccount1.getLastName())) {
                            throw new RuntimeException(Color.ANSI_RED + "ПИШИТЕ ПРАВИЛЬНО!" + Color.ANSI_RESET);
                        }
                    } catch (RuntimeException a) {
                        System.out.println(a.getMessage());
                        break;
                    }
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

        UserAccount userAccount1 = new UserAccount();
        checkUser(cardNumber, pinCode);
        System.out.println(Color.ANSI_GREEN + "Ваш баланс: " + Color.ANSI_RESET + "" + userAccount1.getBalance());


    }

    @Override
    public void deposit(String cardNumber, String pinCode) {
        UserAccount userAccount = new UserAccount();
        try {
            userAccount = checkUser(cardNumber, pinCode);
//        for (UserAccount userAccount1 : accountDao.getUserAccounts()) {
//            if (cardNumber.equals(userAccount1.getCardNumber()) && pinCode.equals(userAccount1.getPinCode())) {
//                userAccount = userAccount1;
//            }
//        }

            if (!cardNumber.equals(userAccount.getCardNumber()) && !pinCode.equals(userAccount.getPinCode())) {
                throw new RuntimeException(Color.ANSI_RED + "Неправильно номер карта или pin-код!" + Color.ANSI_RESET);
            }
            System.out.println("Сколько вы хотите внести на депозит");
            int d = scanner.nextInt();
            System.out.println(Color.ANSI_GREEN + "Баланс успешно пополнен" + Color.ANSI_RESET);
            userAccount.setBalance(userAccount.getBalance() + d);
            System.out.println(Color.ANSI_GREEN + "Ваш баланс: " + userAccount.getBalance() + "" + Color.ANSI_RESET);
        } catch (RuntimeException a) {
            System.out.println(a.getMessage());
//                System.out.println(Color.ANSI_RED + "ПИШИТЕ ПРАВИЛЬНО!" + Color.ANSI_RESET);
        }
    }

    public UserAccount checkUser(String cardNumber, String pinCode) {
        UserAccount userAccount = new UserAccount();
        for (UserAccount userAccount1 : accountDao.getUserAccounts()) {
            if (cardNumber.equals(userAccount1.getCardNumber()) && pinCode.equals(userAccount1.getPinCode())) {
                userAccount = userAccount1;
            }
        }
        return userAccount;
    }

    @Override
    public void sendToFriend(String friendCardNumber) {
        System.out.print("Введите номер своей карты: ");
        String CardNumber = String.valueOf(scanner.nextInt());
        System.out.println("напишите pin-код");
        String PinCode = String.valueOf(scanner.nextInt());
        try {
            for (UserAccount userAccount : accountDao.getUserAccounts()) {
                if (userAccount.getCardNumber().equals(CardNumber) && userAccount.getPinCode().equals(PinCode)) {
                    for (UserAccount account : accountDao.getUserAccounts()) {
                        account.getCardNumber().equals(friendCardNumber);
                        System.out.print("Введите сумму которую хотите отправить : ");
                        int s = scanner.nextInt();
                        if (userAccount.getBalance() >= s) {
                            account.setBalance(s);
                            userAccount.setBalance(userAccount.getBalance() - s);
                            System.out.println(Color.ANSI_GREEN + "Ваш баланс: " + userAccount.getBalance() + Color.ANSI_RESET);
                            break;
                        }
                        if (!userAccount.getCardNumber().equals(CardNumber) && !userAccount.getPinCode().equals(PinCode)) {
                            throw new RuntimeException(Color.ANSI_RED + "ПИШИТЕ ПРАВИЛЬНО!" + Color.ANSI_RESET);

                        }
                    }
//        UserAccount friendAccount= new UserAccount();
//        System.out.println("Сколько денег хотите перевести");
//        int s=scanner.nextInt();
//        for (int i = 0; i <accountDao.getUserAccounts().size() ; i++) {
//            UserAccount  userAccount=accountDao.getUserAccounts().get(i);
//            if (userAccount.getCardNumber().equals(friendCardNumber)){
//          friendAccount=userAccount;
//            }
//        }
//        for (int i = 0; i <accountDao.getUserAccounts().size() ; i++) {
//            UserAccount userAccount=accountDao.getUserAccounts().get(i);
//            if (userAccount.getCardNumber().equals())
//        }
                }
            }
        } catch (RuntimeException a) {
            System.out.println(a.getMessage());
        }
    }

    @Override
    public void withdrawMoney(int amount) {
        System.out.print("Напишите номер карты  ");
        String CardNumber = String.valueOf(scanner.nextInt());
        System.out.print("Напишите pin-код  ");
        String PinCode = String.valueOf(scanner.nextInt());
        if (amount % 1000 == 0) {
            int pieces = amount / 1000;
            System.out.println("Получить " + amount + " рублей по (" + pieces + ") 1000 рублёвыми купюрами (нажмите кнопку 1)");
        }
        if (amount % 500 == 0) {
            int pieces = amount / 500;
            System.out.println("Получить " + amount + " рублей по (" + pieces + ") 500 рублёвыми купюрами (нажмите кнопку 2)");
        }
        if (amount % 200 == 0) {
            int pieces = amount / 200;
            System.out.println("Получить " + amount + " рублей по (" + pieces + ") 200 рублёвыми купюрами (нажмите кнопку 3)");
        }
        if (amount % 100 == 0) {
            int pieces = amount / 100;
            System.out.println("Получить " + amount + " рублей по (" + pieces + ") 100 рублёвыми купюрами (нажмите кнопку 4)");
        }
        if (amount % 50 == 0) {
            int pieces = amount / 50;
            System.out.println("Получить " + amount + " рублей по (" + pieces + ") 50 рублёвыми купюрами (нажмите кнопку 5)");
        }
        if (amount % 10 == 0) {
            int pieces = amount / 10;
            System.out.println("Получить " + amount + " рублей по (" + pieces + ") 10 рублёвыми монетами (нажмите кнопку 6)");
        }
        System.out.print("Введите номер действия: ");
        int number = scanner.nextInt();
        for (UserAccount account : accountDao.getUserAccounts()) {
            int balance = account.getBalance() - amount;
            if (number == 1 && account.getCardNumber().equals(CardNumber) && account.getBalance() >= amount && account.getPinCode().equals(PinCode)) {
                account.setBalance(balance);
                System.out.println("Ваш баланс: " + account.getBalance());
                System.out.println("Выведено: " + amount + " рублей по 1000 рублей (" + amount / 1000 + " штук).");

            } else if (number == 2 && account.getCardNumber().equals(CardNumber) && account.getBalance() >= amount) {
                account.setBalance(balance);
                System.out.println("Ваш баланс: " + account.getBalance());
                System.out.println("Выведено: " + amount + " рублей по 500 рублей (" + amount / 500 + " штук).");

            } else if (number == 3 && account.getCardNumber().equals(CardNumber) && account.getBalance() >= amount) {
                account.setBalance(balance);
                System.out.println("Ваш баланс: " + account.getBalance());
                System.out.println("Выведено: " + amount + " рублей по 200 рублей (" + amount / 200 + " штук).");

            } else if (number == 4 && account.getCardNumber().equals(CardNumber) && account.getBalance() >= amount) {
                account.setBalance(balance);
                System.out.println("Ваш баланс: " + account.getBalance());
                System.out.println("Выведено: " + amount + " рублей по 100 рублей (" + amount / 100 + " штук).");

            } else if (number == 5 && account.getCardNumber().equals(CardNumber) && account.getBalance() >= amount) {
                account.setBalance(balance);
                System.out.println("Ваш баланс: " + account.getBalance());
                System.out.println("Выведено: " + amount + " рублей по 50 рублей (" + amount / 50 + " штук).");

            } else if (number == 6 && account.getCardNumber().equals(CardNumber) && account.getBalance() >= amount) {
                account.setBalance(balance);
                System.out.println(Color.ANSI_GREEN + "Ваш баланс: " + account.getBalance() + Color.ANSI_RESET);
                System.out.println("Выведено: " + amount + " рублей по 10 рублей (" + amount / 10 + " штук).");
            }
        }
    }

    public void account() {
        for (UserAccount userAccount : accountDao.getUserAccounts()) {
            singIn(userAccount.getName(), userAccount.getLastName());
        }
    }

    public void English() {

    }
}