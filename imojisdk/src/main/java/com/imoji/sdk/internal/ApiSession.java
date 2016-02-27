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

package com.imoji.sdk.internal;

import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.imoji.sdk.ImojiSDKTask;
import com.imoji.sdk.RenderingOptions;
import com.imoji.sdk.StoragePolicy;
import com.imoji.sdk.objects.Category;
import com.imoji.sdk.objects.Imoji;
import com.imoji.sdk.response.CategoriesResponse;
import com.imoji.sdk.response.CreateImojiResponse;
import com.imoji.sdk.response.ImojisResponse;
import com.imoji.sdk.response.NetworkResponse;
import com.imoji.sdk.response.RenderResponse;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.Future;

public class ApiSession extends NetworkSession {

    public ApiSession(@NonNull StoragePolicy storagePolicy) {
        super(storagePolicy);
    }

    @NonNull
    @Override
    public Future<CategoriesResponse> getImojiCategories(@NonNull Category.Classification classification) {
        return validatedGet("imoji/categories/fetch", CategoriesResponse.class,
                Collections.singletonMap("classification", classification.name().toLowerCase()), null
        );
    }

    @NonNull
    @Override
    public Future<ImojisResponse> searchImojis(@NonNull String term,
                                               @Nullable Integer offset,
                                               @Nullable Integer numberOfResults) {
        final HashMap<String, String> params = new HashMap<>(3);

        params.put("query", term);
        if (offset != null) {
            params.put("offset", offset.toString());
        }

        if (numberOfResults != null) {
            params.put("numResults", numberOfResults.toString());
        }

        return validatedGet("imoji/search", ImojisResponse.class, params, null);
    }

    @NonNull
    @Override
    public Future<ImojisResponse> getFeaturedImojis(@Nullable Integer numberOfResults) {
        final HashMap<String, String> params = new HashMap<>(1);

        if (numberOfResults != null) {
            params.put("numResults", numberOfResults.toString());
        }

        return validatedGet("imoji/featured/fetch", ImojisResponse.class, params, null);
    }

    @Override
    public Future<ImojisResponse> fetchImojisByIdentifiers(@NonNull List<String> identifiers) {
        final String ids = TextUtils.join(",", identifiers);
        return validatedPost("imoji/fetchMultiple", ImojisResponse.class, Collections.singletonMap("ids", ids), null);
    }

    @Override
    public Future<ImojisResponse> searchImojisWithSentence(@NonNull String sentence, @Nullable Integer numberOfResults) {
        final HashMap<String, String> params = new HashMap<>(2);

        params.put("sentence", sentence);

        if (numberOfResults != null) {
            params.put("numResults", numberOfResults.toString());
        }

        return validatedGet("imoji/search", ImojisResponse.class, params, null);
    }

    @NonNull
    @Override
    public Future<RenderResponse> renderImoji(@NonNull Imoji imoji, @NonNull RenderingOptions options) {
        return null;
    }

    @NonNull
    @Override
    public Future<ImojisResponse> getImojisForAuthenticatedUser() {
        return validatedGet("user/imoji/fetch", ImojisResponse.class, null, null);
    }

    @NonNull
    @Override
    public Future<NetworkResponse> addImojiToUserCollection(@NonNull Imoji imoji) {
        return validatedPost("user/imoji/collection/add",
                NetworkResponse.class,
                Collections.singletonMap("imojiId", imoji.getIdentifier()),
                null
        );
    }

    @NonNull
    @Override
    public Future<CreateImojiResponse> createImojiWithRawImage(@NonNull Bitmap rawImage, @NonNull Bitmap borderedImage, @Nullable List<String> tags) {
        return null;
    }

    @NonNull
    @Override
    public Future<NetworkResponse> removeImoji(@NonNull Imoji imoji) {
        return validatedDelete("imoji/remove", NetworkResponse.class, Collections.singletonMap("imojiId", imoji.getIdentifier()), null);
    }

    @NonNull
    @Override
    public Future<NetworkResponse> reportImojiAsAbusive(@NonNull Imoji imoji,
                                                        @Nullable String reason) {
        final HashMap<String, String> params = new HashMap<>(2);

        params.put("imojiId", imoji.getIdentifier());
        params.put("reason", reason);

        return validatedPost("imoji/reportAbusive", NetworkResponse.class, params, null);
    }

    @NonNull
    @Override
    public Future<NetworkResponse> markImojiUsage(@NonNull Imoji imoji,
                                                  @Nullable String originIdentifier) {
        final HashMap<String, String> params = new HashMap<>(2);

        params.put("imojiId", imoji.getIdentifier());

        if (originIdentifier != null) {
            params.put("originIdentifier", originIdentifier);
        }

        return validatedGet("analytics/imoji/sent", NetworkResponse.class, params, null);
    }

    @NonNull
    private <T extends NetworkResponse> ImojiSDKTask<T> resolveTask(Future<T> task) {
        ImojiSDKTask<T> imojiSDKTask = new ImojiSDKTask<T>() {
        };
        imojiSDKTask.execute(task);

        return imojiSDKTask;
    }
}
