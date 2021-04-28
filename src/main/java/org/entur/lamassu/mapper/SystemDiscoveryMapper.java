/*
 *
 *
 *  * Licensed under the EUPL, Version 1.2 or – as soon they will be approved by
 *  * the European Commission - subsequent versions of the EUPL (the "Licence");
 *  * You may not use this work except in compliance with the Licence.
 *  * You may obtain a copy of the Licence at:
 *  *
 *  *   https://joinup.ec.europa.eu/software/page/eupl
 *  *
 *  * Unless required by applicable law or agreed to in writing, software
 *  * distributed under the Licence is distributed on an "AS IS" basis,
 *  * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  * See the Licence for the specific language governing permissions and
 *  * limitations under the Licence.
 *
 */

package org.entur.lamassu.mapper;

import org.entur.lamassu.model.discovery.FeedProvider;
import org.entur.lamassu.model.discovery.System;
import org.entur.lamassu.model.gbfs.v2_1.GBFSFeedName;
import org.entur.lamassu.util.FeedUrlUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class SystemDiscoveryMapper {
    @Value("${org.entur.lamassu.baseUrl}")
    private String baseUrl;

    public System mapSystemDiscovery(FeedProvider feedProvider) {
        System mapped = new System();
        mapped.setId(feedProvider.getSystemId());
        mapped.setUrl(FeedUrlUtil.mapFeedUrl(baseUrl, GBFSFeedName.GBFS, feedProvider));
        return mapped;
    }
}
