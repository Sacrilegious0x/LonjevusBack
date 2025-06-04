package cr.ac.ucr.ie.Lonjevus.jpa;

import cr.ac.ucr.ie.Lonjevus.domain.Billing;
import cr.ac.ucr.ie.Lonjevus.repository.IBillingRepository;
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

    @PersistenceContext
    private EntityManager entityManager;

    // Obtener todas las facturas activas
    public List<Billing> getAllActive() {
        return repository.findAllByIsActiveTrue();
    }

    // Buscar por ID
    public Billing findById(Integer id) {
        return repository.findById(String.valueOf(id))
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
    @Transactional
    public void update(Integer id, Billing updatedBilling) {
        repository.updatePurchase(
                String.valueOf(id),
                Date.valueOf(updatedBilling.getDate()),
                updatedBilling.getAmount()
        );
    }

    // Eliminado lógico
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
