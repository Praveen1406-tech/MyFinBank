package com.myfinbank.myfinbank.service.imp;

import com.myfinbank.myfinbank.Repo.CustomerRepository;
import com.myfinbank.myfinbank.Repo.TransactionRepository;
import com.myfinbank.myfinbank.model.Customer;
import com.myfinbank.myfinbank.model.Transaction;
import com.myfinbank.myfinbank.service.CustomerService;
//import com.myfinbank.myfinbank.service.TransactionService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;


@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService{
	
	private final CustomerRepository customerRepository;
    //private final TransactionService transactionRepository;
    private final TransactionRepository transactionRepository;

	@Override
	public Customer register(Customer customer) {
		return customerRepository.save(customer);
	}
		
	@Override
	public Customer Update(Long account_no,Customer updateData)
	{
		Customer existingCustomer = customerRepository.findById(account_no).orElseThrow(() -> new RuntimeException("Customer not found"));;
		
		existingCustomer.setName(updateData.getName());
        existingCustomer.setAccountType(updateData.getAccountType());
        existingCustomer.setBalance(updateData.getBalance());
        existingCustomer.setEmail(updateData.getEmail());
        existingCustomer.setPassword(updateData.getPassword());
        
        return customerRepository.save(existingCustomer);
		
	}
	
    @Override
    public void delete(Long account_no) {
        customerRepository.deleteById(account_no);
    }
    
    @Override
    public List<Customer> listAll() {
        return customerRepository.findAll();
    }
    
    @Override
    public Customer getByEmail(String email) {
        return customerRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Customer not found with email: " + email));
    }
    @Override
    public Customer getById(Long accountNo) {
        return customerRepository.findById(accountNo)
                .orElseThrow(() -> new RuntimeException("Customer not found"));
    }


    @Override
    @Transactional
    public void transfer(Long fromAcc, Long toAcc, Double amount) {

        // 1️⃣ Load sender customer
        Customer from = customerRepository.findById(fromAcc)
                .orElseThrow(() -> new RuntimeException("Sender account not found"));

        // 2️⃣ Load receiver customer
        Customer to = customerRepository.findById(toAcc)
                .orElseThrow(() -> new RuntimeException("Receiver account not found"));

        // 3️⃣ Daily withdrawal limit check (BEFORE deducting amount)
        LocalDateTime start = LocalDate.now().atStartOfDay();
        LocalDateTime end = start.plusDays(1);

        Double todayDebits = transactionRepository.getTotalDebitsForDay(fromAcc, start, end);
        if (todayDebits == null) todayDebits = 0.0;

        if (todayDebits + amount > 50000) {
            throw new RuntimeException("Daily withdrawal limit exceeded");
        }

        // 4️⃣ Check if sender has enough balance
        if (from.getBalance() < amount) {
            throw new RuntimeException("Insufficient balance");
        }

        // 5️⃣ Deduct & Add balance
        from.setBalance(from.getBalance() - amount);
        to.setBalance(to.getBalance() + amount);

        customerRepository.save(from);
        customerRepository.save(to);

        // 6️⃣ Add Debit Transaction
        Transaction debit = new Transaction();
        debit.setCustomer(from);
        debit.setAmount(amount);
        debit.setType("DEBIT");
        debit.setRemark("Transfer to " + toAcc);
        debit.setCreatedAt(LocalDateTime.now());
        transactionRepository.save(debit);

        // 7️⃣ Add Credit Transaction
        Transaction credit = new Transaction();
        credit.setCustomer(to);
        credit.setAmount(amount);
        credit.setType("CREDIT");
        credit.setRemark("Received from " + fromAcc);
        credit.setCreatedAt(LocalDateTime.now());
        transactionRepository.save(credit);
    }



}
