/*
 * Imoji Android SDK
 * Created by nkhoshini
 *
 * Copyright (C) 2016 Imoji
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to
 * deal in the Software without restriction, including without limitation the
 * rights to use, copy, modify, merge, publish, distribute, sublicense, and/or
 * sell copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KID, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING
 * FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS
 * IN THE SOFTWARE.
 *
 */

package io.imoji.sdk.objects;

import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.Collections;
import java.util.List;

/**
 * A category object represents an opaque grouping of Imoji's.
 */
public class Category {

    /**
     * Defines a high level grouping of category types
     */
    public enum Classification {
        /**
         * Allows for the caller to obtain all trending and time sensitive categories
         * (ex: sporting events, holidays, etc).
         */
        Trending,

        /**
         * Allows for the caller to obtain categories of imojis that are not time sensitive
         * (ex: emotions, locations, people, etc).
         */
        Generic,

        /**
         * Allows for the caller to obtain all categories containing artist content
         */
        Artist,

        /**
         * Allows for the caller to obtain all categories.
         */
        None
    }

    /**
     * Describes the type of attribution URL
     */
    public enum URLCategory {
        /**
         * The provided URL will link to a website.
         */
        Website,
        /**
         * The provided URL will link to an Instagram profile page.
         */
        Instagram,
        /**
         * The provided URL will link to a video (ex: Youtube, Vimeo, etc).
         */
        Video,
        /**
         * The provided URL will link to a Twitter Profile page.
         */
        Twitter,
        /**
         * The provided URL will link to a landing page in the Apple App Store.
         */
        AppStore
    }

    /**
     * Represents the attribution of the category
     */
    public static class Attribution {

        @Nullable
        private final String identifier;


        @Nullable
        private final Artist artist;

        @Nullable
        private final Uri uri;

        @Nullable
        private final URLCategory urlCategory;

        @NonNull
        private final List<String> relatedTags;

        @NonNull
        private final Imoji.LicenseStyle licenseStyle;

        public Attribution(@Nullable String identifier, @Nullable Artist artist, @Nullable Uri uri,
                           @NonNull List<String> relatedTags, @Nullable URLCategory urlCategory,
                           @NonNull Imoji.LicenseStyle licenseStyle) {
            this.identifier = identifier;
            this.artist = artist;
            this.uri = uri;
            this.urlCategory = urlCategory;
            this.relatedTags = relatedTags;
            this.licenseStyle = licenseStyle;
        }

        /**
         * @return A unique id for the attribution record
         */
        @Nullable
        public String getIdentifier() {
            return identifier;
        }

        /**
         * @return The artist/contributor information
         */
        @Nullable
        public Artist getArtist() {
            return artist;
        }

        /**
         * @return A punchout URL for the attribution
         */
        @Nullable
        public Uri getUri() {
            return uri;
        }

        /**
         * @return Classification of the URL.
         */
        @Nullable
        public URLCategory getUrlCategory() {
            return urlCategory;
        }

        @NonNull
        public List<String> getRelatedTags() {
            return relatedTags;
        }

        /**
         * @return The license style for the category attribution object.
         */
        @NonNull
        public Imoji.LicenseStyle getLicenseStyle() {
            return licenseStyle;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Attribution that = (Attribution) o;

            return identifier != null ? identifier.equals(that.identifier) : that.identifier == null;
        }

        @Override
        public int hashCode() {
            return identifier != null ? identifier.hashCode() : 0;
        }
    }

    @NonNull
    private final String identifier;

    @NonNull
    private final String title;

    @NonNull
    private final List<Imoji> previewImojis;

    @Nullable
    private final Attribution attribution;

    public Category(@NonNull String identifier,
                    @NonNull String title,
                    @NonNull List<Imoji> previewImojis,
                    @Nullable Attribution attribution) {
        this.identifier = identifier;
        this.title = title;
        this.previewImojis = Collections.unmodifiableList(previewImojis);
        this.attribution = attribution;
    }

    /**
     * @return A unique id for the category
     */
    @NonNull
    public String getIdentifier() {
        return identifier;
    }

    /**
     * @return Description of the category.
     */
    @NonNull
    public String getTitle() {
        return title;
    }

    /**
     * @return One or more Imoji objects representing the category.
     */
    @NonNull
    public List<Imoji> getPreviewImojis() {
        return previewImojis;
    }

    /**
     * @return A imoji representing the category
     */
    @NonNull
    public Imoji getPreviewImoji() {
        return previewImojis.iterator().next();
    }

    /**
     * @return The attribution details associated with the category. This field can be null when the
     * category does not contain artist content.
     */
    @Nullable
    public Attribution getAttribution() {
        return attribution;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Category category = (Category) o;

        if (!identifier.equals(category.identifier)) return false;
        if (!title.equals(category.title)) return false;
        if (!previewImojis.equals(category.previewImojis)) return false;
        return !(attribution != null ? !attribution.equals(category.attribution) : category.attribution != null);

    }

    @Override
    public int hashCode() {
        int result = identifier.hashCode();
        result = 31 * result + title.hashCode();
        result = 31 * result + previewImojis.hashCode();
        result = 31 * result + (attribution != null ? attribution.hashCode() : 0);
        return result;
    }
}
