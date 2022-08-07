package my.project.accman.service;

import my.project.accman.model.Account;

/**
 * This Class is responsible for orchestrating all the logic for Creating, Retrieving, Updating and Deleting Accounts.
 */
public interface AccountService {
    /**
     * Method used to find Accounts by UUID
     * @param uuid the UUID of the Account
     * @return an Account
     */
    Account findAccountByUUID(String uuid);

    /**
     * Method used for creating/persisting a new Account.
     * @param account the account to create.
     * @return an echo the account.
     */
    Account addAccount(Account account);

    /**
     * Method for updating an existing Account.
     * @param updatedAccount the account meta data to update.
     * @param uuid the UUID of the account to update.
     * @return an echo (the latest version) of the updated account.
     */
    Account updateAccountByUUID(Account updatedAccount, String uuid);

    /**
     * Method used for deleting existing Accounts.
     * @param uuid the UUID of the account to delete.
     * @return true if successful, false if deletion failed.
     */
    boolean deleteAccountById(String uuid);
}
