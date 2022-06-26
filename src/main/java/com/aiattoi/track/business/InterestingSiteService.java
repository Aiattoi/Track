package com.aiattoi.track.business;

import com.aiattoi.track.dao.InterestingSiteJpaRepository;
import com.aiattoi.track.domain.InterestingSite;
import com.aiattoi.track.domain.Track;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.Collection;

@Component
public class InterestingSiteService extends AbstractCrudService<Integer, InterestingSite, InterestingSiteJpaRepository> {

    public InterestingSiteService(InterestingSiteJpaRepository interestingSiteRepository) {
        super(interestingSiteRepository);
    }

    @Transactional
    public void updateWithTracks(TrackService trackService, InterestingSite entity)
            throws NoEntityFoundException {

        if (readById(entity.getId()).isPresent()) {
            InterestingSite old = readById(entity.getId()).get();
            old.getTracks().forEach((t) -> t.removeSite(old));
            old.getTracks().forEach(entity::addTrack);
            repository.save(entity);
            for (Track track : old.getTracks()) {
                track.addSite(entity);
                trackService.update(track);
            }
        } else
            throw new NoEntityFoundException(InterestingSite.getEntityName());
    }

    public void addTrack(TrackService trackService, InterestingSite site, Integer trackId)
            throws NoEntityFoundException {

        Track track = trackService.readById(trackId).orElseThrow(
                () -> new NoEntityFoundException(Track.getEntityName())
        );
        site.addTrack(track);
        update(site);
        track.addSite(site);
        trackService.update(track);
    }

    public void deleteTrack(TrackService trackService, InterestingSite site, Integer trackId)
            throws NoEntityFoundException {

        Track track = trackService.readById(trackId).orElseThrow(
                () -> new NoEntityFoundException(Track.getEntityName())
        );
        site.deleteTrack(track);
        update(site);
        track.removeSite(site);
        trackService.update(track);
    }

    public void deleteByIdWithTracks(TrackService trackService, Integer id) {
        if (readById(id).isPresent()) {
            InterestingSite site = readById(id).get();
            for (Track track : site.getTracks()) {
                track.removeSite(site);
                trackService.update(track);
            }
        }
        deleteById(id);
    }

    @Override
    protected boolean exists(InterestingSite entity) {
        return repository.existsById(entity.getId());
    }

    public Collection<InterestingSite> listInterestingSitesGreaterThan(Integer altitude) {
        return repository.getInterestingSitesByAltitudeGreaterThan(altitude);
    }
}
