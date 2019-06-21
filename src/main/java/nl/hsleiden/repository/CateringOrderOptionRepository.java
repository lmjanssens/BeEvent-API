package nl.hsleiden.repository;

        import nl.hsleiden.model.CateringOrder;
        import nl.hsleiden.model.CateringOrderOption;
        import org.springframework.data.jpa.repository.JpaRepository;
        import java.util.List;

public interface CateringOrderOptionRepository extends JpaRepository<CateringOrderOption, Long> {
    List<CateringOrderOption> findCateringOrderOptionByCateringorder(CateringOrder cateringOrder);

    @Override
    List<CateringOrderOption> findAll();
}