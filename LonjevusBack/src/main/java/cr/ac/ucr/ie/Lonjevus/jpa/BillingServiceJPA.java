package cr.ac.ucr.ie.Lonjevus.jpa;

import cr.ac.ucr.ie.Lonjevus.domain.Admin;
import cr.ac.ucr.ie.Lonjevus.domain.Billing;
import cr.ac.ucr.ie.Lonjevus.domain.Resident;
import cr.ac.ucr.ie.Lonjevus.repository.IAdminRepository;
import cr.ac.ucr.ie.Lonjevus.repository.IBillingRepository;
import cr.ac.ucr.ie.Lonjevus.repository.IResidentRepository;
import cr.ac.ucr.ie.Lonjevus.service.IBillingService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.ParameterMode;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.StoredProcedureQuery;
import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class BillingServiceJPA implements IBillingService {

    @Autowired
    private IBillingRepository repository;

    @Autowired
    private IAdminRepository adminRepository;

    @Autowired
    private IResidentRepository residentRepository;

    @PersistenceContext
    private EntityManager entityManager;

    // Obtener todas las facturas activas
    public List<Billing> getAllActive() {
        return repository.findAllByIsActiveTrue();
    }

    // Buscar por ID
    public Billing findById(Integer id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Factura no encontrada"));
    }

    // Guardar nueva factura
    @Transactional
    public void save(Billing billing) {
        StoredProcedureQuery query = entityManager.createStoredProcedureQuery("insert_billing");

        query.registerStoredProcedureParameter("p_date", Date.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("p_amount", BigDecimal.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("p_period", String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("p_payment_method", String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("p_admin_id", Integer.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("p_resident_id", Integer.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("p_new_consecutive", String.class, ParameterMode.OUT);

        query.setParameter("p_date", Date.valueOf(billing.getDate()));
        query.setParameter("p_amount", billing.getAmount());
        query.setParameter("p_period", billing.getPeriod());
        query.setParameter("p_payment_method", billing.getPaymentMethod());
        query.setParameter("p_admin_id", billing.getAdministrator() != null ? billing.getAdministrator().getId() : null);
        query.setParameter("p_resident_id", billing.getResident().getId());

        query.execute();

        String newConsecutive = (String) query.getOutputParameterValue("p_new_consecutive");
        billing.setConsecutive(newConsecutive);
    }

    // Actualizar factura
    @Override
    @Transactional
    public void update(Integer id, Billing updatedBilling) {
        Billing existingBilling = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Factura no encontrada"));

        existingBilling.setAmount(updatedBilling.getAmount());
        existingBilling.setDate(updatedBilling.getDate());
        existingBilling.setPeriod(updatedBilling.getPeriod());
        existingBilling.setPaymentMethod(updatedBilling.getPaymentMethod());
        existingBilling.setIsActive(updatedBilling.getIsActive());

        // Obtener admin y residente existentes por ID
        Admin existingAdmin = adminRepository.findById(updatedBilling.getAdministrator().getId())
                .orElseThrow(() -> new RuntimeException("Admin no encontrado"));

        Resident existingResident = residentRepository.findById(updatedBilling.getResident().getId())
                .orElseThrow(() -> new RuntimeException("Residente no encontrado"));

        existingBilling.setAdministrator(existingAdmin);
        existingBilling.setResident(existingResident);

        repository.save(existingBilling);
    }

    // Eliminado lógico
    @Override
    @Transactional
    public void delete(Integer id) {
        repository.deleteBillingLogically(id);
    }

    // Buscar por fecha
    public List<Billing> findByDate(LocalDate date) {
        return repository.findByDate(date);
    }

    // Buscar por período
    public List<Billing> findByPeriod(String period) {
        return repository.findByPeriodContainingIgnoreCase(period);
    }
}
