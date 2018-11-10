package org.fpoly.nhom2.service;

import org.fpoly.nhom2.entiry.ViewCount;
import org.fpoly.nhom2.repository.ViewCountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * ViewCountService
 */
@Component
public class ViewCountService {

    @Autowired
    ViewCountRepository viewCountRepository;

    public ViewCount addNew(){
        ViewCount vc = new ViewCount();
        vc.setCount(0);
        return viewCountRepository.save(vc);
    }

    @Async
    public void increaseViewCount(ViewCount viewCount){
        int current = viewCount.getCount();
        current++;
        viewCount.setCount(current);
        viewCountRepository.save(viewCount);
    }
}