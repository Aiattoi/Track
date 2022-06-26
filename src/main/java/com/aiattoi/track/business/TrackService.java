package com.aiattoi.track.business;

import com.aiattoi.track.dao.TrackJpaRepository;
import com.aiattoi.track.domain.InterestingSite;
import com.aiattoi.track.domain.Manager;
import com.aiattoi.track.domain.Track;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.Collection;

@Component
public class TrackService extends AbstractCrudService<Integer, Track, TrackJpaRepository> {
    private final ManagerService managerService;

    public TrackService(TrackJpaRepository trackRepository, ManagerService managerService) {
        super(trackRepository);
        this.managerService = managerService;
    }

    @Transactional
    public void updateWithSitesAndManager(InterestingSiteService isService, Track track)
            throws NoEntityFoundException {

        if (readById(track.getId()).isPresent()) {
            Track old = readById(track.getId()).get();
            old.getInterestingSites().forEach((is) -> is.deleteTrack(old));
            if (old.getManager().isPresent())
                old.getManager().get().deleteTrack(old);
            old.getInterestingSites().forEach(track::addSite);
            if (old.getManager().isPresent())
                track.setManager(old.getManager().get());

            update(track);

            for (InterestingSite site : old.getInterestingSites()) {
                site.addTrack(track);
                isService.update(site);
            }
            if (old.getManager().isPresent()) {
                old.getManager().get().addTrack(track);
                managerService.update(old.getManager().get());
            }
        } else
            throw new NoEntityFoundException(Track.getEntityName());
    }

    public void updateManager(Track track, Integer managerId)
            throws NoEntityFoundException {

        Manager manager = managerService.readById(managerId).orElseThrow(
                () -> new NoEntityFoundException(Manager.getEntityName())
        );
        track.setManager(manager);
        update(track);
        manager.addTrack(track);
        managerService.update(manager);
    }

    public void deleteManager(Track track) {
        if (track.getManager().isPresent()) {
            track.getManager().get().deleteTrack(track);
            managerService.update(track.getManager().get());
        }
        track.deleteManager();
        update(track);
    }

    public void addSite(InterestingSiteService isService, Track track, Integer isId)
            throws NoEntityFoundException {

        InterestingSite site = isService.readById(isId).orElseThrow(
                () -> new NoEntityFoundException(InterestingSite.getEntityName())
        );
        track.addSite(site);
        update(track);
        site.addTrack(track);
        isService.update(site);
    }

    public void deleteSite(InterestingSiteService isService, Track track, Integer isId)
            throws NoEntityFoundException {

        InterestingSite site = isService.readById(isId).orElseThrow(
                () -> new NoEntityFoundException(InterestingSite.getEntityName())
        );
        track.removeSite(site);
        update(track);
        site.deleteTrack(track);
        isService.update(site);
    }

    public void deleteByIdWithSitesAndManager(InterestingSiteService isService, Integer id) {

        if (readById(id).isPresent()) {
            Track track = readById(id).get();
            for (InterestingSite site : track.getInterestingSites()) {
                site.deleteTrack(track);
                isService.update(site);
            }
            if (track.getManager().isPresent()) {
                track.getManager().get().deleteTrack(track);
                managerService.update(track.getManager().get());
            }
        }
        deleteById(id);
    }

    @Override
    protected boolean exists(Track entity) {
        return repository.existsById(entity.getId());
    }

    public Collection<Track> listTracksWithoutManager() {
        return repository.getTracksByManagerIsNull();
    }
}
