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

package io.imoji.sdk;

import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.Date;
import java.util.List;

import io.imoji.sdk.objects.Category;
import io.imoji.sdk.objects.CategoryFetchOptions;
import io.imoji.sdk.objects.CollectionType;
import io.imoji.sdk.objects.Imoji;
import io.imoji.sdk.response.CategoriesResponse;
import io.imoji.sdk.response.CreateImojiResponse;
import io.imoji.sdk.response.GenericApiResponse;
import io.imoji.sdk.response.ImojiAttributionsResponse;
import io.imoji.sdk.response.ImojisResponse;

/**
 * Base interface for generating any Imoji Api Request
 *
 * @author nkhoshini
 */
public interface Session {

    /**
     * Fetches top level imoji categories given a classification type.
     *
     * @param classification Type of category classification to retrieve
     * @return An ApiTask reference to be resolved by the caller
     * @deprecated Use getImojiCategorys(CategoryFetchOptions fetchOptions) instead
     */
    @NonNull
    ApiTask<CategoriesResponse> getImojiCategories(@NonNull Category.Classification classification);

    /**
     * Fetches top level imoji categories with options.
     *
     * @param fetchOptions Options to use for filtering category requests
     * @return An ApiTask reference to be resolved by the caller
     */
    @NonNull
    ApiTask<CategoriesResponse> getImojiCategories(@NonNull CategoryFetchOptions fetchOptions);

    /**
     * Searches the imojis database with a given search term.
     *
     * @param term Search term to find imojis with. If null or empty, the server will typically return the featured set of imojis (this is subject to change).
     * @return An ApiTask reference to be resolved by the caller
     */
    @NonNull
    ApiTask<ImojisResponse> searchImojis(@NonNull String term);

    /**
     * Searches the imojis database with a given search term.
     *
     * @param term            Search term to find imojis with. If null or empty, the server will typically returned the featured set of imojis (this is subject to change).
     * @param offset          The result offset from a previous search.
     * @param numberOfResults Number of results to fetch.
     * @return An ApiTask reference to be resolved by the caller
     */
    @NonNull
    ApiTask<ImojisResponse> searchImojis(@NonNull String term, @Nullable Integer offset, @Nullable Integer numberOfResults);

    /**
     * Gets a random set of featured imojis.
     *
     * @return An ApiTask reference to be resolved by the caller
     */
    @NonNull
    ApiTask<ImojisResponse> getFeaturedImojis();

    /**
     * Gets a random set of featured imojis
     *
     * @param numberOfResults Number of results to fetch.
     * @return An ApiTask reference to be resolved by the caller
     */
    @NonNull
    ApiTask<ImojisResponse> getFeaturedImojis(@Nullable Integer numberOfResults);

    /**
     * Gets imojis associated to a user's collection which can be accumulated by calling either
     * createImojiWithRawImage (Created),  addImojiToUserCollection (Saved),
     * or markImojiUsage (Recents).
     *
     * @param collectionType CollectionType to filter on. If null, all collected Imoji's are returned
     * @return An ApiTask reference to be resolved by the caller
     */
    @NonNull
    ApiTask<ImojisResponse> getCollectedImojis(@Nullable CollectionType collectionType);

    /**
     * Gets corresponding Imoji's for one or more imoji identifiers as Strings
     *
     * @param identifiers One or more Imoji ID's
     * @return An ApiTask reference to be resolved by the caller
     */
    @NonNull
    ApiTask<ImojisResponse> fetchImojisByIdentifiers(@NonNull List<String> identifiers);


    /**
     * Searches the imojis database with a complete sentence. The service performs keyword parsing to find best matched imojis.
     *
     * @param sentence Full sentence to parse.
     * @return An ApiTask reference to be resolved by the caller
     */
    @NonNull
    ApiTask<ImojisResponse> searchImojisWithSentence(@NonNull String sentence);

    /**
     * Searches the imojis database with a complete sentence. The service performs keyword parsing to find best matched imojis.
     *
     * @param sentence        Full sentence to parse.
     * @param numberOfResults Number of results to fetch.
     * @return An ApiTask reference to be resolved by the caller
     */
    @NonNull
    ApiTask<ImojisResponse> searchImojisWithSentence(@NonNull String sentence, @Nullable Integer numberOfResults);


    /**
     * Adds an Imoji sticker to the database
     *
     * @param rawImage      The Imoji sticker image
     * @param borderedImage The bordered version of the Imoji sticker image
     * @param tags          An array of Strings representing the tags of the Imoji sticker
     * @return An ApiTask reference to be resolved by the caller
     */
    @NonNull
    ApiTask<CreateImojiResponse> createImojiWithRawImage(@NonNull Bitmap rawImage,
                                                         @NonNull Bitmap borderedImage,
                                                         @Nullable List<String> tags);

    /**
     * Removes an Imoji sticker that was created by the user with createImojiWithImage:tags:callback:
     *
     * @param imoji The added Imoji object
     * @return An ApiTask reference to be resolved by the caller
     */
    @NonNull
    ApiTask<GenericApiResponse> removeImoji(@NonNull Imoji imoji);

    /**
     * Reports an Imoji sticker as abusive. You may expose this method in your application in order for users to have the ability to flag
     * content as not appropriate. Reported Imojis are not removed instantly but are reviewed internally before removal.
     *
     * @param imoji  The Imoji object to report
     * @param reason Optional text describing the reason why the content is being reported
     * @return An ApiTask reference to be resolved by the caller
     * @deprecated Use reportImojiAsAbusive(String imojiId, String reason) instead
     */
    @NonNull
    @Deprecated
    ApiTask<GenericApiResponse> reportImojiAsAbusive(@NonNull Imoji imoji, @Nullable String reason);

    /**
     * Reports an Imoji sticker as abusive. You may expose this method in your application in order for users to have the ability to flag
     * content as not appropriate. Reported Imojis are not removed instantly but are reviewed internally before removal.
     *
     * @param imojiId The ID of the Imoji object to report
     * @param reason  Optional text describing the reason why the content is being reported
     * @return An ApiTask reference to be resolved by the caller
     */
    @NonNull
    ApiTask<GenericApiResponse> reportImojiAsAbusive(@NonNull String imojiId, @Nullable String reason);

    /**
     * Marks an Imoji sticker as being used for sharing. For example, if a user copied a sticker
     * in a keyboard application, that would qualify as the Imoji being used.
     *
     * @param imoji            The Imoji object to register for usage
     * @param originIdentifier Optional arbitrary identifier which developers can supply describing the action that
     *                         triggered the usage. String must be less than or equal to 40 characters.
     * @return An ApiTask reference to be resolved by the caller
     * @deprecated Use markImojiUsage(String imojiId, String originIdentifier) instead
     */
    @NonNull
    ApiTask<GenericApiResponse> markImojiUsage(@NonNull Imoji imoji, @Nullable String originIdentifier);

    /**
     * Marks an Imoji sticker as being used for sharing. For example, if a user copied a sticker
     * in a keyboard application, that would qualify as the Imoji being used.
     *
     * @param imojiId          The ID of the Imoji object to register for usage
     * @param originIdentifier Optional arbitrary identifier which developers can supply describing the action that
     *                         triggered the usage. String must be less than or equal to 40 characters.
     * @return An ApiTask reference to be resolved by the caller
     */
    @NonNull
    ApiTask<GenericApiResponse> markImojiUsage(@NonNull String imojiId, @Nullable String originIdentifier);

    /**
     * Adds a given Imoji sticker to a users collection. The content can be fetched by calling
     * getCollectedImojis with CollectionType.Liked for the type.
     *
     * @param imojiId          The ID of the Imoji object to add
     * @return An ApiTask reference to be resolved by the caller
     */
    @NonNull
    ApiTask<GenericApiResponse> addImojiToUserCollection(@NonNull String imojiId);

    /**
     * Gets attribution information for a set of Imoji ID's.
     *
     * @param identifiers One or more Imoji ID's
     * @return An ApiTask reference to be resolved by the caller
     */
    @NonNull
    ApiTask<ImojiAttributionsResponse> fetchAttributionByImojiIdentifiers(@NonNull List<String> identifiers);

    /**
     * Gets attribution information for a set of Imoji ID's.
     *
     * @param gender      An optional gender value for the user (either 'male' or 'female')
     * @param latitude    Optional latitude value for the user's location
     * @param longitude   Optional longitude value for the user's location
     * @param dateOfBirth Optional date of birth value for the user
     * @return An ApiTask reference to be resolved by the caller
     */
    @NonNull
    ApiTask<GenericApiResponse> setUserDemographicsData(@Nullable String gender,
                                                        @Nullable Double latitude,
                                                        @Nullable Double longitude,
                                                        @Nullable Date dateOfBirth);

}
