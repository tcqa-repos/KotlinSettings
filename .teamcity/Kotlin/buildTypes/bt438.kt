package Kotlin.buildTypes

import Kotlin.buildTypes.bt390
import jetbrains.buildServer.configs.kotlin.v10.*
import jetbrains.buildServer.configs.kotlin.v10.BuildFeature
import jetbrains.buildServer.configs.kotlin.v10.BuildFeature.*
import jetbrains.buildServer.configs.kotlin.v10.buildSteps.AntBuildStep
import jetbrains.buildServer.configs.kotlin.v10.buildSteps.AntBuildStep.*
import jetbrains.buildServer.configs.kotlin.v10.buildSteps.MavenBuildStep
import jetbrains.buildServer.configs.kotlin.v10.buildSteps.MavenBuildStep.*
import jetbrains.buildServer.configs.kotlin.v10.buildSteps.ScriptBuildStep
import jetbrains.buildServer.configs.kotlin.v10.buildSteps.ScriptBuildStep.*
import jetbrains.buildServer.configs.kotlin.v10.buildSteps.ant
import jetbrains.buildServer.configs.kotlin.v10.buildSteps.maven
import jetbrains.buildServer.configs.kotlin.v10.buildSteps.script
import jetbrains.buildServer.configs.kotlin.v10.triggers.FinishBuildTrigger
import jetbrains.buildServer.configs.kotlin.v10.triggers.FinishBuildTrigger.*
import jetbrains.buildServer.configs.kotlin.v10.triggers.VcsTrigger
import jetbrains.buildServer.configs.kotlin.v10.triggers.VcsTrigger.*
import jetbrains.buildServer.configs.kotlin.v10.triggers.finishBuildTrigger
import jetbrains.buildServer.configs.kotlin.v10.triggers.vcs

object bt438 : BuildType({
    uuid = "3c091b75-0232-47e3-aa0e-3ad51ea9fe2e"
    extId = "bt438"
    name = "Deploy Maven Artifacts (Deprecated)"

    allowExternalStatus = true
    buildNumberPattern = "%dep.bt345.build.number%"

    params {
        param("DeployVersion", "1.1-SNAPSHOT")
        param("jdk17.home", "%env.JDK_17%")
        param("jdk18.home", "%env.JDK_18%")
        password("kotlin.key", "zxx0ead0f89b53aab8ca9f047db82e8c26bf851ffcd3e40676b82a038547d52fa59d89b34ab97cd6f4d1cd63adcb95f07bfc35853d1e5119e279f288363782748bfbe770eca7cee9c718768440d21cf784ec95b4664b2b4e12c31c9e5a6c77792681c247748c382aa6c9f58d3fc4a9944b86f2cc93536f9bb6941b0417b998b09b624ca13b9a9911e0985a2afd5451afc1ac7df163bf1f006e1bfae32666ea13784b12e2a862e5ad89f2cce5470ba417ba386f3d04be5508650fdc2d4ed0b2561bf8c94522414878fc20a78211d2d8a3e41e675353229f58d794894aa7c6d610372b79b1b5f77602b4aa1f0c67759f65831592e2afa45179cd58af14972c7314e280978f2693f34b6306abff1d77ffa68f88eacd8665b0151caf3a1fa4ea85571014e8f1c7cf55bc1960bfa6388348a3f6994954a80533594975eb90d55e6579e1bfa90eb80452e4ef715cfa3cd460582c4c8304d97cfb55514911ac22d0fad46cd6417ac2a9898f0aef06c3061faf3b685858ec4c9b18dee3cf26e0c7eebda6fb1326227a093a05231dd8a18c0a65229eb30a568d86d35cf95dd393fa878890c62fd5d2ab3882e6070f3c76c887bbe3afd0cb936bd4a53d7b1ab8d171f10c3819027e70c5890be4bb937754b01b9603036c805a12381cf74fd18812e555d92e6f49e1fee5f4da6bce163c5aee601b16d07d03e4fc600ab0a9f8ea381f6aafbbbe235a64eacc3f9919b77d665567c1f36aba9dd525fac8903c2ec0ef3885413259772fe1d72bd9d9a301796f0f1620301e1e5541b0b101c6ecd9cf1eb16e03d742a5c3ab8a7b20636c4d9aeb286fcbd4d30e5b129b121c417c66172bc426bf6e4e34ed03d0e5ba2ba7c086702664b23e0dc7cca3c90dd3f24b13978cd8923faefb81f29a21ea460ec247d4918a31985db0ef65a3d7a7089b97ba8fb75526db3926edfd784a5f723eb0103287802a6e7e6874cba6819d0f56a08e1f8a23b0582a0da6d623b5be0d536791ce86842dd1c15b50ea51af756aaf1f074baa4d11f84d2542346cea2e864c377e0165b6b552b0e4806fbd0c4d1efc5af95f1c01776aab4443858ec7c34a754a420a7cd1d628d83f73ab1b3b96099503012868e3c80f6903f60baa3fd0ecdf0b6b3566b0a5cb5855ec0ce251ec67613d0a547675af12637ab680e0d03471414ce8f9ad3cef669853ee812f4d21174687d78e543d1817a773220f1e509ae939fc6e87337444a96a70132bb55033a6cefaacffcabfb8fda5c6c71fdcfa55fed02c6250969cfd8bb588a5ab778a9ac8483906e10d3d0641ae0614dffc149eecf6fc509ebd68cfd17c20a20cf719b782fe2431155cf2fd897dd960ad76f90ecfc7a75cde704b9246a9dcd0623bdd519357b110e1915155c61138c50c1750047d3f22d3b34b42d50dbdb0449f590fb03c0a3928d85b336a88ac554ef2c3d5ad0c2466bf20d10e4e0aa4ca8cb19b36e9229f6c05d3e14c00bed3b1ecd3f503e048e8a06debaae04f7b94d1dfaa4577b7bc8293057a8dec89f57806c4adfa956d4e7fe006cfbc1dd207dec6e49edc419082f0931c634937efd3f8ab8a1e41d84c73760a6cd0d1b46569da6ae9923d8628bd81cc03032cd3166ce25b2c7033d286b90441ed5f90057cf494dc6110613006da8b4a92e7bc372bc3c6f538b445e76fe6af7c25b5c2b68bbaf2965bfadab33dc0a08efa810313fb5dc811a96f6f143651e0b8ae1fc3590a47077bbbe9fc7285dffc0710f20c427c0fb48cdf7bbb0e25d82df2f2f36c7ba244f45cd4ab3fb15980d5c62486aebdb485273cbb253cf15f033a8c13777026b3e733eb93bccee8519d76a052b8531f80d8986258b352fc913c9db4fd791bb1b4ee10b3099853186f6b49d39be3f31432b3bfc826a8a7727c0d716e4b37e23aaee2f173d36cc339a48a712672c8729f1f860717efc2ed0f3fce2470487ad123b5402dd619ca174a9ebb2c1ff497b4152f0f0e04adf0794ad9c9027a0e5c44256fd228731a9f82a42a51515b1c13e8a1a3b82a19b40e34f42849d68580481e842252d0080cbf184287f338629d6265c1f5acf5341cb35ec9642b7445c0e979844dbc496809eeeb9e55e32dec2116d6d2a5d3cb2f2ca106a30c279b7fc9a7f7d8cbfc491b7f629925749d12b3b391bb4c6f1674be8bbdde9d520f3a0f95bfa3e200b4568b8d3d37814960169d9b8881042fdc8a34db7199d3193df6621c2d0aa2bf0820e0ee5249112d5d187d707648dc5a94f9101561838313cdb414cfa013db4a9ffc934795fae35e07193dd70090d3b37eb49fc436278294cdf0ffcc4162b86fd3bf8ecbcfce865426a38c4e6f33a3280d37ee47acf8dd0539cdba68f18aaebf55dfbafecea7412975295919ba95b150ddf9326c830391f9ff058795d2c53fd9f8e5423b07975090ab19d2c2154ad26bcbfc6176a78dde73e6a6c78c7cb4df59905380664947748ef9cc7863e6085c32d7aad6d123324b9336e7a9c97d79244183c45e59fc77e8b57b5b9f8e7e7a6b227df84c72b8a041434d619f5179e70e11dbf1d505ead684fdf2160438ab05417740f5e34d8311e1957db55387a4ec28a8cc1780f48906b7968b229649ee31ff748262fd4315a20b388b020e70a994fd5b54c4ddb6bd1c2fbc259e075d3ee91b951055540f498b002bd0ea0c074c3db39704388afbceb30a22f221eca899caf8d3d470763d2b31010a0bed52322611396898985adeb3c6600f27d280c7ff538230b00ec8e264f0a82e9e1ef8885dd677a7b10116c9c975365579a47c622d310432c5ce0a8edaff4b31ba71d414bd16e6b5fb2c574b7d3e1a12983188f3823b1686ff2d59375af81655a4a586d8968c76e8069dffb7590fe038b797f15332ccc98940f23a7ad44dbd6320141ac6c8645a399039ef688e54b59a2d0f39f2bc5cd9ee7b6c3d637c02e797e51844e3d507592449327402a74204039e4a343ffa3e1b2e33fa6dc4c59eb37a6274b6126ad58ecc22b3f27b63dfc5c44420ab1747bc68e3c5dfefe3342b370c2a895b33a8f93e46f43a8146918c0d68d7eac4f5cf8c67ae757f2ce86758a6136a120d166e3fa125b36bd8031c346dd2a31d1125903cd5d15dcbb61dc4e6d367ac6f67c02a9d952169b70f6006b42872dd13358c82fdfb26d5316dd13c2db52181e9e765e72d3a3a417a5370ba06974714c3b582a66073e7835220932600536d7acef437150e85cf90d6ce98a90d8ff3e48eb46f9475ecc314dc7715177ffa12a176a83168bef4bca04c4b947df164a6e9b57926ddd26c9d572e07af568b083f3182f1d4eb421042e7e038b9c6f731258e6dfdfd4e8641257b20aa997ef2b132ffb38f200d385a4d22e5da8a2748518777c9759f6d793b62ead076cf4f53cd97bd2d1a6ec825ff2d1297ecc78ef480bf41941212311d42bdb7ffcf62c7cde9de4c78e80474e716bf388742e5491baf01e147fa820f1ee4d7b7d091fe0c288a5be17549f5939115ed2e481940112d58cda6e51cc63cf2d648f07a46238d6f950eeedfc40ed2fc8635c86b47886e0a971b13f7f4f412165e60b45cc76e66c4b23c509104161326ba29ac69636096287e6316c1d36032d3cfb86ff9302857c8cfbf85c1f273fb176e8f14a4fabb6dba5c3b20233b2b48209ea56d3c96f19baa0318859b68cd9c8fa17f9ca392719c43755744d2aa482d95b37021c83d7dfd028f1584f81d5662aea15d376375715f487ec2d9fd20d815a2b704f67c7ccffbdc17009535b983902e80f7b301fb3ae31151e7eee864785cd6616994e8e85c1ce25569b9c7cb7e288cc0261e7e7863eec17af9cd5307f19011c02501028167737ea061b0d632476e39cb9bcf531558ed9eb70fa000720029f148849fe00bc0ad3834e00231131a9f304a679cbb714948938f958f3785f545ef6d956636ca2bcfef987265eac0e57faba2948510134dd4f772e7b0d03436c0d1745b1e36bce0eb907da3807d166a243e6bda52535b9c7bb5eada4afdae796b81c8c3d6321f074ebb167d6f5b3ffdf7ac08c0c9d658d03136dcd013ed3838bc8aaf85d031b6fe49dd260b91e48356d326b2e3874dac1e619c5f4efc5304607b7cb0a1fefdc022b0dcb7045933d890478290d0336e5f08d50257336ccf89d94894ef47bddb107752e60896ff02f502145cf21896961774076a87fca6de92747eb17a8c19cb69e9dbf46a0ede4ef68c0912742c58fa6ef7fe92a51e4423a185e609d2b99ff548f172623caa00a6dc0da5ea9fcaa963f4e7ce0d9e26a1366945db97e7adff8dc4895fbeda07ffb3aa374571030d59599ee73bb74b983020b238367dde1dfdd99946826409516b518ff6a2f1c913d95d8347c3569441d22e33682240efd0c812fff553c5f1db0ee125fab7c283cb50a24e3f729f969c14251df7db9e6e36feaaa9d2ca003031e18e1e46d70a84da1d1915f3e38af4f4e67a512071acb2867196ea01b25c92b8e82efea5f5137a07aebb850afe23bd81cd4ad1b03e0397bc1226d352995a005eea98a728fedc01d0747d81defea1f6d59e77e3caefc46295ebfa1d00914adec01baa270365faf02c51cfe6cff2c79d826d84cd785c3823b2d984feac2751b32928f0d57565fec84b8f20e5ff646cc34691015ba7188e8895135629e603f2961ad6c13190508c8f7fc83aa8a3bc0457700f05f0bdac3dc18839b357afef3b1f4de34caf285018feed0e859458d2f1015810ddb750ead530ee2a9dd00c4d06cd9caa2175cc91bbc86417939aa20634f34fa27e0373c5cc49dc40a685e4fbfb9beef407bab10981a1b18a44a92259cdddadc28d67fe8cc3b749e92a6b88ce9b9cb78c9f312c84c59d51caa7efcfecbf1a3822f4431ffd5f382a5e5ad6fb7e9ae5a26a971d0be292e2c0b97819a9c61380527dcfae804e1a51de5cc1197931b10938d26b541ab9e8b4e7de0e1672b4765ce2c081f3b9c1f39b83b65fe6d5bf20c2d6f7a72fd14d8d2a97b5314414f024d50b12b895c222a33249b976a5d1c1699854d01855496c4e5f6788ad5849e65368f9b43403d98461dbdb9912c5d0f5d2d5c79c0c82419edb987329dc902116b9fecfe6be99266e97537aca0916459523475f0b9927a04fab82bd6271cbde13f30b66b480d00b4bf9250bd7f6c57341f480d7c1fcd78249b902e908804025cf88452d21cda576c5d356c4fe5ecd5e2b35972aff967f3d759fd4ce19730a85200d1919c3c1d3794d9f7a8484784be75568221968e481b1d4cea252e4ca6dcc6e7ecce498e30819da3b43073d24fd5619ca88b6c4ec674a994ae5f7002c7505a97a070d79234ba88291a0dffd836c8f5395bf7150de5d92152b886e964cf84d1378c828a7c3aef2788605362e11579614735a5ff995c980fda44c1a360e894b3872156b352a2b36ff91bce960b054caa82d469d655aa85b15b642dd97d732b3cb990ed2b38bd1ac9fdbdb181d004ea783f6c429e3cb58c0f0084b6985ef41f28ae7bedb2587d5da3d49fee35a881bbf44c850903064275e0137d4c565b7802e514468d0b8354b05a9956bfa91b0c5ea555f9e6bd3454dcc0b87e82ca54713ff200d122adb7528e00ee913bfcd9ce1f9a84663a4ab33f8a65416dd82b0e1c5cbb9e911393e0c4b82e55cb89d26ae74453e19425846a1162032eff2dba6862484d87ff390506d53351ee44b414f5e455b36046bc23abba6c1fd8683a1b50825e94361ad43ff0cc970bb27a14c1bc0c0c8007c57d481071c7d91a4f3cb61472c6b4a3a24ddbaa1afac094732dcc2921f179369ede021b45ac5a5dec8c3e37c7a911832f7dba350d3808198d6d3f54d9f2a5c298c01227b6a55f9e66920ad23e1269ad4806622072ea70d18e2d8d023c79e780ecbc54174294fd3ea0869eb0b72968ef639652cadfc7c67dcebfa35e0e0bf5004b09418fc7cf0ec8fd664fa1958f779a455dd957eeda602fdfd73af43192518d1a1715fd757ae1e89882be86a92bd5096e1230c2b3a5cd86ba5ed8c50ef09c33dbcffc5ed779553ecf7eba887d0c1bbc8823faa61b3711e8abe030236b3fa3bb64f719d1b35770c433a807670808072ae3df1cf03bb96540f94f06e44bc89227a5e589585b372b17bffe005c3376e50c45f6f4a6780cdf78a58039de2eb6e001205a5a37ba530aa6182820c6f918b48b9c858fc91e179e281a1a62554484cabb99abb75b8456898dfa8ec4dfcd441d8f75eaa7c361cbc4485bbe67eee8207fc3308a7ac3e0df9ed6ea33dd0aefb882f82126645002a28e9257ed5a5c3d02afb84fc54ba1cdf55f7beacfa9d2fd81e6b47e512826797cf1d949cea7f9221575619ad93fa4bf7f326a4319d721e84f56893a9aa827dd89ddca2365300f22479e1c747307ab42ff255051843b639990c8b1bd32db4dfc7fa692d76777ab196fd66bdc5f0bd5d5c53781d9f7570f5ffd2b08d7041e38d0f02e7b0859467e25611c55f75fcd0709172b9e9ad651830912edf690eb6e0a7f18dabc9710c08eb9fca321762e66f8eefb4f9967f9770ec5e60032fe699c7a44cc127f38c80ba77650a13d5165d44b6cd3fab4499cefe7382baaf8c1d722374efc588c05caf99225df68a4678a2ae2cd165aa44c73c9b469b0ed699c2c663c7f792a7643c3f50714976c52bc9747a177537fbc479017b7b75cadcfdc78b44eb683bb2a698b9a0828b5b7ae3bd36a75b014a022be3648347e6d2844a5c3e7034d4971582bc48f3de10614caf221c47d0045e7f02d0dc8717a64196776e46219684158acd465aa98dc9074815d204e6a1d3e976047616c715c9b98fdcc7bd17efa1a996a071600eaa9b6db6355252cbc9d8f04dad5e8314822c799279e5a894ed4d9c02ddef604de40d1b64965e01348cc09c398a9307791b72e42120cb88b1e084d2cb37b935faac03ac4639eaadb4dbc17d622c7921f1784859e97c6f915c59bbf9495eb3698226c1eea241603779996fbb893b475cb948a8ff4df39ba711965e0b7a6fff272ccd8410200ba4ba84325ac97aef961adc7ac02062d84f2e9ea5eef0c97c4053c56bb8d8c5be58d7f6c9107ecbf0008e5bfdb7cb721c23e7350181bb563db2694ceb91b45b1e36b6b5c7bea20ecfd68e4b25e5ecc7e89b6203e77f25ce0dd8928647c3b03bd6ef19cc608789a7a96343df8a6de923962fa488c19175db70ec2e6705907d1dbbbd19b2bee960f1dcc569ab8d58f483b5a8b631443d20fd9e98d68aeebaa942c67941750d542d56902326909e28cf78401528e90fd4ee93a986859e1265b1388f2d549cd6389f67f508e504b8b47b8076f96212d53d544ba48d0355f803454072f06955e805869b541f11b4fde90630a4172965f29a1c79fda194ad057b9c98b783811ab60235b11a711d2f3dc4b4e4cb606554323fa645a2e277ff40a52c496bb6ef4a749995db65bea71f6f35bd5e0047a587400f6f13581d6cdc2abf2874e7177e16e857764e6bb1c4a860e2fe63ff2a3d5c6f1561b11a225ecb97cfbdfee44831cadb55b4e87a517579f31286482803a7a5eeaf5521ab71033deec3e0db7dd814f916e3b33e5defff5477a23249c8d5d1a56ff60a844e6c5f5c56e01711dace398b35426da41e796225444d6cdcc4e64f6ac9b537efc51e7259629c5ea5542abe1d7177fc58643c522a07f9db8c699097a16115ef1222cb166816ea60e66f8bea726264ef38f38581d1672d8c30cbf041a58b1b4e3fd8931b931658df929840a89d99b7a1a76e12d751a8004f449027b8d1a97ca2b692ae1f7005cb06d74a97f033c36a80d31513eb792d8c883292d468e298eb1f2ad95bf1462ab67ccf93ddb39863fbef0c2e6b27b73518cd79dfe9465d920e8fddccb5f3ace1e28210ed8e5faf2023c5895df1620a7b8bca85c53d26cb961aa6bf5965d704fa700d48d269e1e396e09f134864543d399de0860f27530750f5beb80b5cc6cee0c5c6a4b4874fc7294d9d1555189d1493ad76ef2eb1427d24a042554500bf9920417ead9aaf60272ed8445f072023c8da9926f364787eb2f5d180e9e2d8a90039bc1c0ef7379ab2f3b3caedcd7c862d51c3ff9081f44c84010a0d0e43beb1cbd1f3c859201b69d28c47f596b645529d37afba9d6054282692125c95ddcfea7e96299f033362abb72a0fded57eefa8b82b3ad71b550456161070a4135d431b65484c4de8d4ee81a6aa43a892bd62fdabb9fa906f040e9d55a5012264e9fad39bccc063990a0b08f850b04b8f6dc2169f51ee7f358ac0fd8cd065ef63c40a7eadaafce6e7ccd3e76f326dc11b641a01e8393e768cf622180e6375f26e8225b9e4d6e3d8c40c6901007a50145e26d9ecb946a7eaac161d3e3f3cee1ba8ceac9bf4a03181fe28456596618ec72114b2407576290d4ca56eb7ac2ad838fabcbaf8ba2b72496f2273a1e2f96ee289c686ecef10c550b1bd06d3a9aef7d92f010317dc6b36dbc68bbf727ddb60c82fa0baca72a776e94207bd5e3ff882d71b8d9547e83f5bd3509cc6e08ee5a4bd3a7b8a3d0fdaf473a769ddab6223269b53884c15ce309224f5cbaaba19741620ad3834e00231131b8376ed0de8340a3dd22d01e74d7256100bfc0adb56d488170272f7935d56f76fd5b2396e7d2c1262917c7fdf75452897486f0bc89b73e7e0c871d4e2fd6e9c4d48f3a93f7488dd4b04bc059ed4c91effbe9bb1ddc2fc592a98935a2e28ea849000b7d5c62f57c0b2c2411581241a1778b025b525a9461a63d50342540cc26ac686fa849c89d0bdeb6d94e90706ac84abca11298c9499f06abd6427162d419d0ae9f14fce27ae90e6c6ce4c4e203a3353fb2bd0460468088db275fbf30624d5e0df43f4f6c3267590050bee9f2e08301561102d9061f8aa362e4963b758f714e438127273e477ea76cbc52835773e0783595469fbec0c61a3e487a14260ddd26bcbbebd15f5cfa1cc5e0cd1039302120f102f2116bfd252e65565e4179059b5be41a20e7604ef000e9d62d4b31be799b7e058309e922201ce27dd1ab946f6aa0774918b023f1fea2d62e062f9992ecf44c7b3e5a5a3d7102dec5dde167494980bfc98b75dc50427beeda3536c91dcda0bf951cb66760fab7b74bcd02f6d9da0d520b608dede407bf77c6e19905e4e03d30b3d31d32f9978844f110ae20dd8d8cbabf1dd69721f0e91cc16686c46d0dc9ac73867a0c3188560574a997d75556a96900e3faaafaa87e493557f24a2e9e5fbdcde9daa0cf586e1705c446a2fa678d64e05516a76bf395408556d395808ae3fc60f6f3c7207c1486f847fabbfc3331e185d1f97749590c0d8a50a3e328a2bb1626d430f9f66cbf30bb60bae0826239eceba9d13a750b548ea73b95f45bf2d4112698cf411e20f7c0d8b1c74ebad1379b90f302eccf52c1947500ba8ad6d30b2bd061c2b3f31ed22fe7ee0f02b692a571debc75c8b6941500e23a7c4b8cba29b4c41dd3ae83f4fab2c220d3c64834877a8fb959593c43d47ab2759778a51ec47b2c07fdbf421024694d720cc41701b85708aa1be265ea7f0dca1797cfad0161281e91033575aca3d0a3df25249798eec407973752131d0887009c698da694552cfca9ce6a982bb3111b457e56afe4d84560d6931a3a9e6a242ccea66415eb4790e64f17e8188ebf8e35ef361bd90df5", display = ParameterDisplay.HIDDEN)
        select("system.deploy-repo", "sonatype-nexus-snapshots",
                options = listOf("sonatype-nexus-snapshots-repo" to "sonatype-nexus-snapshots", "bintray-repo" to "bintray"))
        select("system.deploy-url", "http://oss.sonatype.org/service/local/staging/deploy/maven2/",
                options = listOf("sonatype-url (maven central)" to "http://oss.sonatype.org/service/local/staging/deploy/maven2/", "bintray-eap-url (publish manually)" to "https://api.bintray.com/maven/kotlin/kotlin-eap/kotlin", "bintray-eap-url (publish automatically)" to "https://api.bintray.com/maven/kotlin/kotlin-eap/kotlin/;publish=1", "bintray-dev-url (publish manually)" to "https://api.bintray.com/maven/kotlin/kotlin-dev/kotlin", "bintray-eap-1.1-url (publish manually)" to "https://api.bintray.com/maven/kotlin/kotlin-eap-1.1/kotlin"))
        password("system.kotlin.bintray.password", "zxx1b35d0c43c199eac801a3594a00e0550dbf2e62380451d0375d657c25ad36e38811fa87a51633ad9775d03cbe80d301b", display = ParameterDisplay.HIDDEN)
        password("system.kotlin.bintray.user", "zxx529dbb75f4cf3ef14423ab58cd8f54b9", display = ParameterDisplay.HIDDEN)
        param("system.kotlin.key.name", "8A99F98A")
        password("system.kotlin.key.passphrase", "zxx2d8e59f9bfb0682b42d683a8d1c537a4468a048c31f01e807a0fcab54f31132be3539aad36189de6ce2af5e8973753dc8f38903b3d45c5ee2bb05528738b23f467bd58bf8625fc017442db755ee44d53ccfb759b52161faa9c0ee5f719e2228459d02642b7d0fe93", display = ParameterDisplay.HIDDEN)
        password("system.kotlin.sonatype.password", "zxx987d5e89bfbe850ad07ccff7723de6a420de8cffa99994164dfc5cdbc40e8d0b", display = ParameterDisplay.HIDDEN)
        password("system.kotlin.sonatype.user", "zxx8c0820aa1a2b34efb2a183b41935b6b2e503ec685476af67", display = ParameterDisplay.HIDDEN)
        param("system.kotlinHome", "%teamcity.build.checkoutDir%/dist/kotlinc")
    }

    vcs {
        root(Kotlin.vcsRoots.Kotlin_KotlinGithub2)

        checkoutMode = CheckoutMode.ON_SERVER
        cleanCheckout = true
    }

    steps {
        ant {
            name = "Check build"
            mode = antScript {
                content = """
                    <project name="AssertRightBuild">
                      <target name="check">
                        <echo message="Branch: %teamcity.build.vcs.branch.Kotlin___github%"/>
                        <fail message="Do not remote run this build" if="build.is.personal" />
                    
                        <fail message="Builds to Maven Central should be published only from master and release branches">
                          <condition>
                            <and>
                            <equals arg1="%system.deploy-repo%" arg2="sonatype-nexus-snapshots"/>
                            <not>
                              <equals arg1="%teamcity.build.vcs.branch.Kotlin___github%" arg2="refs/heads/master"/>
                            </not>
                            <not>
                              <contains string="%teamcity.build.vcs.branch.Kotlin___github%" substring="/bootstrap"/>
                            </not>
                            <not>
                              <contains string="%teamcity.build.vcs.branch.Kotlin___github%" substring="M13"/>
                            </not>
                            <not>
                              <contains string="%teamcity.build.vcs.branch.Kotlin___github%" substring="M14"/>
                            </not>
                            <not>
                              <contains string="%teamcity.build.vcs.branch.Kotlin___github%" substring="M15"/>
                            </not>
                            <not>
                              <matches string="%teamcity.build.vcs.branch.Kotlin___github%" pattern="beta\d+${'$'}"/>
                            </not>
                            <not>
                              <matches string="%teamcity.build.vcs.branch.Kotlin___github%" pattern="bintray"/>
                            </not>
                            <not>
                              <matches string="%teamcity.build.vcs.branch.Kotlin___github%" pattern="rc\d*${'$'}"/>
                            </not>
                            <not>
                              <matches string="%teamcity.build.vcs.branch.Kotlin___github%" pattern="1.0.\d+${'$'}"/>
                            </not>
                            <not>
                              <matches string="%teamcity.build.vcs.branch.Kotlin___github%" pattern="1.1-M\d+${'$'}"/>
                            </not>
                            </and>
                          </condition>
                        </fail>
                    
                        </target>
                    </project>
                """.trimIndent()
            }
            targets = "check"
            param("org.jfrog.artifactory.selectedDeployableServer.deployerUsername", "udalov")
            param("secure:org.jfrog.artifactory.selectedDeployableServer.deployerPassword", "zxx07d67df0d96aad3350e276915eed3f06863eb00272fa1d7f")
        }
        script {
            name = "Prepare gnupg"
            scriptContent = """
                cd libraries
                export HOME=${'$'}(pwd)
                rm -rf .gnupg
                cat >keyfile <<EOT
                %kotlin.key%
                EOT
                gpg --allow-secret-key-import --import keyfile
                rm -v keyfile
            """.trimIndent()
        }
        maven {
            name = "Set Version"
            goals = "versions:set"
            pomLocation = "libraries/pom.xml"
            runnerArgs = "-DnewVersion=%DeployVersion%"
            userSettingsSelection = "jb mirror"
            useOwnLocalRepo = true
            param("org.jfrog.artifactory.selectedDeployableServer.defaultModuleVersionConfiguration", "GLOBAL")
        }
        maven {
            name = "Libraries Deploy"
            goals = "deploy"
            pomLocation = "libraries/pom.xml"
            runnerArgs = "-Dinvoker.skip=true -DskipTests --activate-profiles noTest,sign-artifacts -e -X"
            mavenVersion = bundled_3_2()
            userSettingsSelection = "userSettingsSelection:byPath"
            userSettingsPath = "%system.teamcity.build.checkoutDir%/libraries/maven-settings.xml"
            useOwnLocalRepo = true
            param("jvmArgs", "-Xmx986m -XX:MaxPermSize=350m")
            param("maven.home", "")
            param("org.jfrog.artifactory.selectedDeployableServer.defaultModuleVersionConfiguration", "GLOBAL")
            param("org.jfrog.artifactory.selectedDeployableServer.deployerUsername", "ilya.gorbunov@jetbrains.com")
            param("secure:org.jfrog.artifactory.selectedDeployableServer.deployerPassword", "zxx8c2a7eb4026210ed775d03cbe80d301b")
            param("target.jdk.home", "%env.JDK_16%")
            param("teamcity.build.workingDir", "libraries")
        }
        script {
            name = "Cleanup"
            executionMode = BuildStep.ExecutionMode.ALWAYS
            scriptContent = """
                cd libraries
                rm -rf .gnupg
            """.trimIndent()
        }
    }

    triggers {
        finishBuildTrigger {
            enabled = false
            buildTypeExtId = bt390.extId
            successfulOnly = true
        }
        vcs {
            enabled = false
            triggerRules = """
                -:grammar/**
                -:spec-docs/**
                -:docs/**
            """.trimIndent()
            branchFilter = "+:<default>"
            watchChangesInDependencies = true
        }
    }

    failureConditions {
        executionTimeoutMin = 90
        errorMessage = true
    }

    features {
        feature {
            type = "perfmon"
        }
    }

    dependencies {
        dependency(Kotlin.buildTypes.bt390) {
            snapshot {
                onDependencyFailure = FailureAction.FAIL_TO_START
            }

            artifacts {
                cleanDestination = true
                artifactRules = "** => dist"
            }
        }
        artifacts(Kotlin.buildTypes.bt345) {
            artifactRules = """
                kotlin-compiler*.zip!**=>dist_bk
                kotlin-compiler-javadoc.jar=>dist_bk
                kotlin-compiler-sources.jar=>dist_bk
                internal/native-platform-uberjar.jar=>dependencies
            """.trimIndent()
        }
    }

    requirements {
        contains("teamcity.agent.jvm.os.name", "Linux")
        noLessThan("teamcity.agent.work.dir.freeSpaceMb", "500")
    }
})
