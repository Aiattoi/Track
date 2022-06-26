package com.aiattoi.track.business;

import com.aiattoi.track.dao.ManagerJpaRepository;
import com.aiattoi.track.domain.Manager;
import com.aiattoi.track.domain.Track;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;

@Component
public class ManagerService extends AbstractCrudService<Integer, Manager, ManagerJpaRepository> {
    public ManagerService(ManagerJpaRepository managerRepository) {
        super(managerRepository);
    }

    @Transactional
    public void updateWithTracks(Manager entity) throws NoEntityFoundException {
        Manager old = readById(entity.getId()).orElseThrow(
                () -> new NoEntityFoundException(Manager.getEntityName())
        );

        repository.save(entity);

        if (!old.getTracks().isEmpty()) {
            old.getTracks().forEach((t) -> t.setManager(entity));
            old.getTracks().forEach(entity::addTrack);
        }
    }

    public void deleteByIdWithTracks(Integer id) {
        if (readById(id).isPresent()) {
            Manager manager = readById(id).get();
            manager.getTracks().forEach(Track::deleteManager);
        }
        deleteById(id);
    }

    @Override
    protected boolean exists(Manager entity) {
        return repository.existsById(entity.getId());
    }

}
